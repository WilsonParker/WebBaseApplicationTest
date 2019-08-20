package co.kr.daisomall.web

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.webkit.WebView
import android.widget.Toast
import com.dev.hare.apputilitymodule.util.Logger
import com.dev.hare.apputilitymodule.util.file.PreferenceUtil
import co.kr.daisomall.activity.WindowActivity
import co.kr.daisomall.constants.Constants.URL_KEY
import co.kr.daisomall.util.iNIPay.INIPayUtility
import com.dev.hare.webbasetemplatemodule.util.UrlUtil
import com.dev.hare.webbasetemplatemodule.web.BaseWebChromeClient
import com.dev.hare.webbasetemplatemodule.web.BaseWebView
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewClient
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


class CustomWebView(context: Context, attrs: AttributeSet?) : BaseWebView<WindowActivity>(context, attrs) {
    override var host: String = ""
        set(value) {
            field = value
            webViewCommand = CustomWebViewCommand(context, host, WindowActivity::class, this)
        }
    lateinit var customWebViewClient: BaseWebViewClient<WindowActivity>

    private var closeReUrl: String? = null
    private lateinit var customWebChromeClient: BaseWebChromeClient<WindowActivity>

    /**
     * @TODO 설치 history 주석처리
     */
    override fun init() {
        super.init()
        settings.apply {
            if (PreferenceUtil.isFirst(context)) {
                userAgentString = "$userAgentString/app_first"
                PreferenceUtil.setFirstInstalled(context)
                // BasicHistoryCallService.insertDownloadHistory{}
            }
        }

        setMixedContentAllowed(true)
        setThirdPartyCookiesEnabled(true)
    }

    fun initWebView(activity: Activity) {
        customWebViewClient = object : BaseWebViewClient<WindowActivity>(webViewCommand) {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Logger.log(Logger.LogType.INFO, "shouldOverrideUrlLoading url $url")
                var result = true
                url?.let {
                    // var funResult = if (!defaultLoadingUrl(activity, url)) nicePayLoadingUrl(activity, view, url) else false
                    result = defaultLoadingUrl(activity, url)
                    if(!result)
                        result = INIPayUtility.shouldOverrideUrlLoading(activity, this@CustomWebView, url)
                }
                return if (!result)
                    super.shouldOverrideUrlLoading(view, url)
                else
                    result
            }
        }
        customWebChromeClient = BaseWebChromeClient(context, webViewCommand)

        webChromeClient = customWebChromeClient
        webViewClient = customWebViewClient

        loadUrl(host)
    }

    fun defaultLoadingUrl(activity: Activity, url: String): Boolean {
        when {
            url.startsWith("$URL_KEY://share_link_url=") -> {
                closeReUrl = getUrl()
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

            }
            url.startsWith("$URL_KEY://copyurl#") -> {
                var TempData = url.replace("$URL_KEY://copyurl#reurl==", "")
                try {
                    TempData = URLDecoder.decode(TempData, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                setClipBoardLink(activity, TempData)

                return true

            }
            url.startsWith("$URL_KEY://openurl#") -> {
                var TempData = url.replace("$URL_KEY://openurl#", "")
                try {
                    TempData = URLDecoder.decode(TempData, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TempData))
                activity.startActivity(intent)

                return true

            }
            url.startsWith("$URL_KEY://deliveryTracking#") -> {
                var TempData = url.replace("$URL_KEY://deliveryTracking#", "")
                try {
                    TempData = URLDecoder.decode(TempData, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                return true
            }

            url.startsWith("intent:") -> {
                try {
                    var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    var existPackage = activity.packageManager.getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        activity.startActivity(intent);
                    } else {
                        var marketIntent = Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        activity.startActivity(marketIntent);
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace();
                }
                return true
            }
            url.startsWith("tel:") -> {
                activity.startActivity(Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse(url)
                })
                return true
            }
            url.startsWith("sms:") -> {
                activity.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse(url)))
                return true
            }
            else -> {
                return false
            }
        }
    }

    fun setClipBoardLink(context: Context, link: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", link)
        clipboardManager.primaryClip = clipData
        Toast.makeText(context, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }

    override fun historyBack(url: String, host: String, event: () -> Unit) {
        when {
            UrlUtil.isSameUrl(host, url) -> event()
            canGoBack() -> goBack()
            else -> event()
        }
    }

}