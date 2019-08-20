package co.kr.daisomall.activity


import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.dev.hare.apputilitymodule.util.Logger
import com.dev.hare.apputilitymodule.util.view.BackPressUtil
import co.kr.daisomall.R
import co.kr.daisomall.constants.Constants.APP_URL
import co.kr.daisomall.constants.Constants.IMG_URL
import co.kr.daisomall.push.BasicMobileCallService
import co.kr.daisomall.util.iNIPay.INIPayUtility
import co.kr.daisomall.util.nicePay.NicePayUtility
import co.kr.daisomall.web.AndroidBridge
import co.kr.daisomall.web.CustomWebView
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.socialloginmodule.activity.abstracts.AbstractSocialActivity
import com.dev.hare.webbasetemplatemodule.activity.BaseMainWithIntroImageActivity
import com.dev.hare.webbasetemplatemodule.activity.BaseWindowActivity
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewCommand
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass

/**
 * Main 위에 intro image 를 띄우는 Activity 입니다
 *
 * @author      Hare
 * @added       2019-06-27
 * @updated     2019-06-27
 * */
class MainWithIntroActivity : BaseMainWithIntroImageActivity() {
    override val windowActivity: KClass<BaseWindowActivity> = WindowActivity::class as KClass<BaseWindowActivity>
    override val introImageID
        get() = R.drawable.intro2
    override val introImageView: ImageView
        get() = intro_image
    override val mainView: ViewGroup
        get() = Layout_main
    override val splashTimeOut: Long
        get() = 2500

    companion object {
        var mainWebView: CustomWebView? = null
    }

    /**
     * @TODO introImage 주석 처리
     */
    override fun init() {
        BasicMobileCallService.getIntroImageUrl {
            Logger.log(Logger.LogType.INFO, "getIntroImageUrl : ${it.toString()}")
            loadIntro(IMG_URL + it?.data?.get("url").toString())
        }
    }

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        mainWebView = webview
    }

    /**
     * @TODO insert token 주석처리
     */
    override fun onCreateAfter(savedInstanceState: Bundle?) {
        FirebaseUtil.getToken(object : FirebaseUtil.OnGetTokenSuccessListener {
            override fun onSuccess(token: String) {
                /*if (FirebaseUtil.isTokenRestored(token)) {
                    BasicTokenCallService.insertToken(
                        this@MainWithIntroActivity,
                        token,
                        CommonUtil.getDeviceUUID(this@MainWithIntroActivity)
                    ) {
                        Logger.log(Logger.LogType.INFO, "token sequence : ${it?.toString()}")
                    }
                } else {
                    HttpConstantModel.token_sequence = FirebaseUtil.getTokenSequence()
                    Logger.log(Logger.LogType.INFO, "token is not restored : ${HttpConstantModel.token_sequence}")
                    if (HttpConstantModel.token_sequence == -1) {
                        FirebaseUtil.resetToken()
                    }
                }*/
                HttpConstantModel.token = token
            }
        })
        // Logger.log(Logger.LogType.INFO, "keyhash", "" + KeyHashManager.getKeyHash(this))

        webview.host = getLink(APP_URL)
        webview.javascriptBrideInterface = AndroidBridge(this, webview)
        webview.initWebView(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.log(Logger.LogType.INFO, "activity result ${requestCode} ${resultCode}")

        when {
            requestCode == NicePayUtility.REQUEST_CODE -> {
                data?.let {
                    NicePayUtility.onActivityResult(requestCode, resultCode, it, webview, this)
                }
            }

            requestCode == BaseWebViewCommand.REQUEST_CODE -> {
                Logger.log(Logger.LogType.INFO, "BaseWebViewCommand.KEY_CALLBACK")
                Logger.log(Logger.LogType.INFO, "${data?.getStringExtra(BaseWebViewCommand.KEY_CALLBACK)}")
                data?.getStringExtra(BaseWebViewCommand.KEY_CALLBACK)?.let {
                    webview.webViewCommand?.callResultFunction(it)
                }
            }

            (requestCode or AbstractSocialActivity.RESULT_CODE) >= AbstractSocialActivity.RESULT_CODE -> {
                if (resultCode == AbstractSocialActivity.RESULT_CODE_SUCCESS || resultCode == 0) {
                    data?.let {
                        var accessToken = data.getStringExtra(AbstractSocialActivity.Key.AccessToken.getValue())
                        var refreshToken = data.getStringExtra(AbstractSocialActivity.Key.RefreshToken.getValue())
                        var id = data.getStringExtra(AbstractSocialActivity.Key.Id.getValue())
                        var redirectUrl = data.getStringExtra(AbstractSocialActivity.Key.RedirectUrl.getValue())
                        val url = "${webview.host}$redirectUrl"
                        Logger.log(Logger.LogType.INFO, accessToken, refreshToken, id, url)
                        webview.post {
                            // webview.loadUrl(url)
                            webview.loadUrl("javascript:mm.link(\"$url\")")
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        Logger.log(Logger.LogType.INFO, "WEBVIEW NEW WINDOW ${webview.url}")
        webview.run {
            historyBack(host, url) {
                BackPressUtil.onBackPressed(this@MainWithIntroActivity, {
                    (mainWebView?.javascriptBrideInterface as AndroidBridge).showPopUp()
                }, {
                    finish();
                })
            }
        }
    }

    override fun onCreateDialog(id: Int): Dialog? {//ShowDialog
        when (id) {
            INIPayUtility.DIALOG_PROGRESS_WEBVIEW -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("로딩중입니다. \n잠시만 기다려주세요.")
                dialog.isIndeterminate = true
                dialog.setCancelable(true)
                return dialog
            }

            INIPayUtility.DIALOG_PROGRESS_MESSAGE -> {
            }

            INIPayUtility.DIALOG_ISP -> {
                INIPayUtility.alertIsp = AlertDialog.Builder(this@MainWithIntroActivity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setMessage("모바일 ISP 어플리케이션이 설치되어 있지 않습니다. \n설치를 눌러 진행 해 주십시요.\n취소를 누르면 결제가 취소 됩니다.")
                    .setPositiveButton("설치") { dialog, which ->
                        val ispUrl = "http://mobile.vpay.co.kr/jsp/MISP/andown.jsp"
                        mainWebView?.loadUrl(ispUrl)
                        finish()
                    }
                    .setNegativeButton("취소") { dialog, which ->
                        Toast.makeText(this@MainWithIntroActivity, "(-1)결제를 취소 하셨습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .create()

                return INIPayUtility.alertIsp
            }

            INIPayUtility.DIALOG_CARDAPP -> return INIPayUtility.getCardInstallAlertDialog(
                this@MainWithIntroActivity,
                INIPayUtility.DIALOG_CARDNM
            )
        }//end switch

        return super.onCreateDialog(id)

    }//end onCreateDialog
}
