package com.dev.hare.webbaseapplicationtest.web

import android.app.Activity
import android.content.*
import android.net.Uri
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.dev.hare.webbaseapplicationtest.activity.MainActivity
import com.dev.hare.webbaseapplicationtest.constants.URL_KEY
import com.dev.hare.webbaseapplicationtest.util.NicePayUtility
import com.dev.hare.webbasetemplatemodule.activity.BaseWebActivity
import com.dev.hare.webbasetemplatemodule.web.BaseWebView
import java.io.UnsupportedEncodingException
import java.net.URISyntaxException
import java.net.URLDecoder

class CustomWebView(context: Context, attrs: AttributeSet?) : BaseWebView(context, attrs) {

    override val host: String
        get() = "http://mobile-enter6.dv9163.kro.kr:8001"
    override val windowActivity: Class<BaseWebActivity>
        get() = MainActivity::class.java as Class<BaseWebActivity>

    private var CloseReUrl: String? = null
    private val WAP_URL = "nicepaysample" + "://"
    // MainUrl

    fun initWebView(activity: Activity) {
        webChromeClient = WebChromeClient()
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    if (url.startsWith("$URL_KEY://share_link_url=")) {
                        CloseReUrl = getUrl()
                        var TempUrl: String? = null
                        try {
                            TempUrl = URLDecoder.decode(url.replace("$URL_KEY://share_link_url=", ""), "UTF-8")
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }

                        if (url.matches(".*share.naver.com.*".toRegex())) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TempUrl))
                            activity.startActivity(intent)
                        } else {
                            loadUrl(TempUrl)
                        }
                        return true

                    } else if (url.startsWith("$URL_KEY://copyurl#")) {
                        var TempData = url.replace("$URL_KEY://copyurl#reurl==", "")
                        try {
                            TempData = URLDecoder.decode(TempData, "UTF-8")
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }

                        setClipBoardLink(activity, TempData)

                        return true

                    } else if (url.startsWith("$URL_KEY://openurl#")) {
                        var TempData = url.replace("$URL_KEY://openurl#", "")
                        try {
                            TempData = URLDecoder.decode(TempData, "UTF-8")
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }

                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TempData))
                        activity.startActivity(intent)

                        return true

                    } else if (url.startsWith("$URL_KEY://deliveryTracking#")) {
                        var TempData = url.replace("$URL_KEY://deliveryTracking#", "")
                        try {
                            TempData = URLDecoder.decode(TempData, "UTF-8")
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }

                        TempData = host + TempData

                         /*
                         #Detail

                         val intent = Intent(activity, DetailActivity::class.java)
                         intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                         intent.putExtra("DetailType", "DeliveryTracking")
                         intent.putExtra("PopupUrl", TempData)
                         startActivity(intent)
                         */

                        return true

                    }

                    // call
                    /*if (url.startsWith("tel:")) {

                        val callpermissionlistener = object : PermissionListener() {
                            // 권한수락 한 경우
                            fun onPermissionGranted() {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                                ContextCompat.startActivity(intent)
                            }

                            // 권한수락 안한경우
                            fun onPermissionDenied(deniedPermissions: ArrayList<String>) {}


                        }

                        TedPermission.with(mContext)
                            .setPermissionListener(callpermissionlistener)
                            .setDeniedMessage("거부시 통화 접근이 차단되어 통화가 제한됩니다.")
                            .setPermissions(Manifest.permission.CALL_PHONE)
                            .check()
                        return true
                    }*/

                    // mail
                    /*if (url.startsWith("mailto:")) {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                        ContextCompat.startActivity(intent)
                        return true
                    }*/

                    var intent: Intent? = null
                    // 웹뷰에서 ispmobile  실행한 경우...
                    if (url.startsWith("ispmobile")) {
                        if (NicePayUtility.isPackageInstalled(activity, "kvp.jjy.MispAndroid320")) {
                            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                            return true
                        } else {
                            view?.let {
                                NicePayUtility.installISP(activity, it)
                            }
                            return true
                        }

                        // 웹뷰에서 계좌이체를 실행한 경우...
                    } else if (url.startsWith("kftc-bankpay")) {
                        if (NicePayUtility.isPackageInstalled(activity, "com.kftc.bankpay.android")) {
                            val sub_str_param = "kftc-bankpay://eftpay?"
                            var reqParam = url.substring(sub_str_param.length)
                            try {
                                reqParam = URLDecoder.decode(reqParam, "utf-8")
                            } catch (e: UnsupportedEncodingException) {
                                e.printStackTrace()
                            }

                            reqParam = NicePayUtility.makeBankPayData(reqParam)

                            intent = Intent(Intent.ACTION_MAIN)
                            intent.component =
                                ComponentName(
                                    "com.kftc.bankpay.android",
                                    "com.kftc.bankpay.android.activity.MainActivity"
                                )
                            intent.putExtra("requestInfo", reqParam)
                            activity.startActivityForResult(intent, 1)

                            return true
                        } else {
                            view?.let {
                                NicePayUtility.installKFTC(activity, it)
                            }
                            return true
                        }

                        // 웹뷰에서 안심클릭을 실행한 경우...
                    } else if (url != null && (url.contains("vguard")
                                || url.contains("droidxantivirus")
                                || url.contains("lottesmartpay")
                                || url.contains("smshinhancardusim://")
                                || url.contains("shinhan-sr-ansimclick")
                                || url.contains("v3mobile")
                                || url.endsWith(".apk")
                                || url.contains("smartwall://")
                                || url.contains("appfree://")
                                || url.contains("market://")
                                || url.contains("ansimclick://")
                                || url.contains("ansimclickscard")
                                || url.contains("ansim://")
                                || url.contains("mpocket")
                                || url.contains("mvaccine")
                                || url.contains("market.android.com")
                                || url.startsWith("intent://")
                                || url.contains("samsungpay")
                                || url.contains("droidx3web://")
                                || url.contains("kakaopay")
                                || url.contains("http://m.ahnlab.com/kr/site/download"))
                    ) {

                        try {
                            try {
                                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)

                            } catch (ex: URISyntaxException) {
                                return false
                            }

                            if (url.startsWith("intent")) { //chrome 버젼 방식
                                if (activity.packageManager.resolveActivity(intent, 0) == null) {
                                    val packagename = intent.getPackage()
                                    if (packagename != null) {
                                        val uri = Uri.parse("market://search?q=pname:$packagename")
                                        intent = Intent(Intent.ACTION_VIEW, uri)
                                        activity.startActivity(intent)
                                        return true
                                    }
                                }

                                val uri = Uri.parse(intent.dataString)
                                intent = Intent(Intent.ACTION_VIEW, uri)
                                activity.startActivity(intent)

                                return true
                            } else { //구 방식
                                intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                                //return true;
                            }
                        } catch (e: Exception) {
                            // 결제 샘플에는 없으나, 취소 처리시 스키마값 처리 못할때가 있어서 추가한 부분
                            // 필요없으면 나중에 수정 혹은 삭제해도 무방
                            if (url.contains("tstore://")) {
                                return false
                            } else {

                            }
                        }

                        // ispmobile에서 결제 완료후 스마트주문 앱을 호출하여 결제결과를 전달하는 경우
                    } else if (url.startsWith(WAP_URL)) {
                        val thisurl = url.substring(WAP_URL.length)
                        view?.loadUrl(thisurl)
                        return true
                    } else {
                        //                    view.loadUrl(url);
                        return false
                    }
                    return true
                } else
                    return true
            }
        }
    }

    fun setClipBoardLink(context: Context, link: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", link)
        clipboardManager.primaryClip = clipData
        Toast.makeText(context, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }
}