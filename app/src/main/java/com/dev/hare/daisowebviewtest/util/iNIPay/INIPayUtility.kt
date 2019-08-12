package com.dev.hare.daisowebviewtest.util.iNIPay

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import java.net.URISyntaxException
import java.util.*

object INIPayUtility {
    val DIALOG_PROGRESS_WEBVIEW = 0
    val DIALOG_PROGRESS_MESSAGE = 1
    val DIALOG_ISP = 2
    val DIALOG_CARDAPP = 3
    var DIALOG_CARDNM = ""
    var alertIsp: AlertDialog? = null

    fun getCardInstallAlertDialog(activity: Activity, coCardNm: String): AlertDialog {
        val cardNm = Hashtable<String, String>()
        cardNm["HYUNDAE"] = "현대 앱카드"
        cardNm["SAMSUNG"] = "삼성 앱카드"
        cardNm["LOTTE"] = "롯데 앱카드"
        cardNm["SHINHAN"] = "신한 앱카드"
        cardNm["KB"] = "국민 앱카드"
        cardNm["HANASK"] = "하나SK 통합안심클릭"
        //cardNm.put("SHINHAN_SMART",  "Smart 신한앱");

        val cardInstallUrl = Hashtable<String, String>()
        cardInstallUrl["HYUNDAE"] = "market://details?id=com.hyundaicard.appcard"
        cardInstallUrl["SAMSUNG"] = "market://details?id=kr.co.samsungcard.mpocket"
        cardInstallUrl["LOTTE"] = "market://details?id=com.lotte.lottesmartpay"
        cardInstallUrl["LOTTEAPPCARD"] = "market://details?id=com.lcacApp"
        cardInstallUrl["SHINHAN"] = "market://details?id=com.shcard.smartpay"
        cardInstallUrl["KB"] = "market://details?id=com.kbcard.cxh.appcard"
        cardInstallUrl["HANASK"] = "market://details?id=com.ilk.visa3d"
        //cardInstallUrl.put("SHINHAN_SMART",  "market://details?id=com.shcard.smartpay");//여기 수정 필요!!2014.04.01

        return AlertDialog.Builder(activity)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("알림")
            .setMessage(cardNm[coCardNm]!! + " 어플리케이션이 설치되어 있지 않습니다. \n설치를 눌러 진행 해 주십시요.\n취소를 누르면 결제가 취소 됩니다.")
            .setPositiveButton("설치") { dialog, which ->
                val installUrl = cardInstallUrl[coCardNm]
                val uri = Uri.parse(installUrl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                Log.d("<INIPAYMOBILE>", "Call : $uri")
                try {
                    activity.startActivity(intent)
                } catch (anfe: ActivityNotFoundException) {
                    Toast.makeText(activity, cardNm[coCardNm]!! + "설치 url이 올바르지 않습니다", Toast.LENGTH_SHORT)
                        .show()
                }

//activityFinish();
            }
            .setNegativeButton("취소") { dialog, which ->
                Toast.makeText(activity, "(-1)결제를 취소 하셨습니다.", Toast.LENGTH_SHORT).show()
                activity.finish()
            }
            .create()

    }//end getCardInstallAlertDialog

    fun shouldOverrideUrlLoading(activity: Activity, view: WebView, url: String): Boolean {

        /*
	    	 * URL별로 분기가 필요합니다. 어플리케이션을 로딩하는것과
	    	 * WEB PAGE를 로딩하는것을 분리 하여 처리해야 합니다.
	    	 * 만일 가맹점 특정 어플 URL이 들어온다면
	    	 * 조건을 더 추가하여 처리해 주십시요.
	    	 */

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            var intent: Intent
            try {
                Log.d("<INIPAYMOBILE>", "intent url : $url")
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)

                Log.d("<INIPAYMOBILE>", "intent getDataString : " + intent.dataString)
                Log.d("<INIPAYMOBILE>", "intent getPackage : " + intent.getPackage())

            } catch (ex: URISyntaxException) {
                Log.e("<INIPAYMOBILE>", "URI syntax error : " + url + ":" + ex.message)
                return false
            }
            val uri = Uri.parse(intent.dataString)
            intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                activity.startActivity(intent)
                /*가맹점의 사정에 따라 현재 화면을 종료하지 않아도 됩니다.
	    			    삼성카드 기타 안심클릭에서는 종료되면 안되기 때문에
	    			    조건을 걸어 종료하도록 하였습니다.*/
                if (url.startsWith("ispmobile://")) {
                    // activity.activityFinish()
                }

            } catch (e: ActivityNotFoundException) {
                Log.e("INIPAYMOBILE", "INIPAYMOBILE, ActivityNotFoundException INPUT >> $url")
                Log.e("INIPAYMOBILE", "INIPAYMOBILE, uri.getScheme()" + intent.dataString)

                //ISP
                if (url.startsWith("ispmobile://")) {
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_ISP)
                    return false
                } else if (intent.dataString!!.startsWith("hdcardappcardansimclick://")) {
                    DIALOG_CARDNM = "HYUNDAE"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, 현대앱카드설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("shinhan-sr-ansimclick://")) {
                    DIALOG_CARDNM = "SHINHAN"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, 신한카드앱설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("mpocket.online.ansimclick://")) {
                    DIALOG_CARDNM = "SAMSUNG"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, 삼성카드앱설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("lottesmartpay://")) {
                    DIALOG_CARDNM = "LOTTE"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, 롯데모바일결제 설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("lotteappcard://")) {
                    DIALOG_CARDNM = "LOTTEAPPCARD"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, 롯데앱카드 설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("kb-acp://")) {
                    DIALOG_CARDNM = "KB"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, KB카드앱설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("hanaansim://")) {
                    DIALOG_CARDNM = "HANASK"
                    Log.e("INIPAYMOBILE", "INIPAYMOBILE, 하나카드앱설치 ")
                    view.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    activity.showDialog(DIALOG_CARDAPP)
                    return false
                } else if (intent.dataString!!.startsWith("droidxantivirusweb")) {
                    /** */
                    Log.d("<INIPAYMOBILE>", "ActivityNotFoundException, droidxantivirusweb 문자열로 인입될시 마켓으로 이동되는 예외 처리: ")
                    /** */

                    val hydVIntent = Intent(Intent.ACTION_VIEW)
                    hydVIntent.data = Uri.parse("market://search?q=net.nshc.droidxantivirus")

                    activity.startActivity(hydVIntent)
                } else if (url.startsWith("intent://")) {

                    /**
                     *
                     * > 삼성카드 안심클릭
                     * - 백신앱 : 웹백신 - 인프라웨어 테크놀러지
                     * - package name : kr.co.shiftworks.vguardweb
                     * - 특이사항 : INTENT:// 인입될시 정상적 호출
                     *
                     * > 신한카드 안심클릭
                     * - 백신앱 : TouchEn mVaccine for Web - 라온시큐어(주)
                     * - package name : com.TouchEn.mVaccine.webs
                     * - 특이사항 : INTENT:// 인입될시 정상적 호출
                     *
                     * > 농협카드 안심클릭
                     * - 백신앱 : V3 Mobile Plus 2.0
                     * - package name : com.ahnlab.v3mobileplus
                     * - 특이사항 : 백신 설치 버튼이 있으며, 백신 설치 버튼 클릭시 정상적으로 마켓으로 이동하며, 백신이 없어도 결제가 진행이 됨
                     *
                     * > 외환카드 안심클릭
                     * - 백신앱 : TouchEn mVaccine for Web - 라온시큐어(주)
                     * - package name : com.TouchEn.mVaccine.webs
                     * - 특이사항 : INTENT:// 인입될시 정상적 호출
                     *
                     * > 씨티카드 안심클릭
                     * - 백신앱 : TouchEn mVaccine for Web - 라온시큐어(주)
                     * - package name : com.TouchEn.mVaccine.webs
                     * - 특이사항 : INTENT:// 인입될시 정상적 호출
                     *
                     * > 하나SK카드 안심클릭
                     * - 백신앱 : V3 Mobile Plus 2.0
                     * - package name : com.ahnlab.v3mobileplus
                     * - 특이사항 : 백신 설치 버튼이 있으며, 백신 설치 버튼 클릭시 정상적으로 마켓으로 이동하며, 백신이 없어도 결제가 진행이 됨
                     *
                     * > 하나카드 안심클릭
                     * - 백신앱 : V3 Mobile Plus 2.0
                     * - package name : com.ahnlab.v3mobileplus
                     * - 특이사항 : 백신 설치 버튼이 있으며, 백신 설치 버튼 클릭시 정상적으로 마켓으로 이동하며, 백신이 없어도 결제가 진행이 됨
                     *
                     * > 롯데카드
                     * - 백신이 설치되어 있지 않아도, 결제페이지로 이동
                     *
                     */

                    /** */
                    Log.d("<INIPAYMOBILE>", "Custom URL (intent://) 로 인입될시 마켓으로 이동되는 예외 처리: ")
                    /** */

                    try {

                        var excepIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        val packageNm = excepIntent.getPackage()

                        Log.d("<INIPAYMOBILE>", "excepIntent getPackage : " + packageNm)

                        excepIntent = Intent(Intent.ACTION_VIEW)
                        /*
								가맹점별로 원하시는 방식으로 사용하시면 됩니다.
								market URL
								market://search?q="+packageNm => packageNm을 검색어로 마켓 검색 페이지 이동
								market://search?q=pname:"+packageNm => packageNm을 패키지로 갖는 앱 검색 페이지 이동
								market://details?id="+packageNm => packageNm 에 해당하는 앱 상세 페이지로 이동
							*/
                        excepIntent.data = Uri.parse("market://search?q=$packageNm")

                        activity.startActivity(excepIntent)
                    } catch (e1: URISyntaxException) {
                        Log.e("<INIPAYMOBILE>", "INTENT:// 인입될시 예외 처리  오류 : $e1")
                    }

                }//INTENT:// 인입될시 예외 처리
                /*
	    			//신한카드 SMART신한 앱
	    			else if( intent.getDataString().startsWith("smshinhanansimclick://"))
	    			{
	    				DIALOG_CARDNM = "SHINHAN_SMART";
	    				Log.e("INIPAYMOBILE", "INIPAYMOBILE, Smart신한앱설치");
	    				view.loadData("<html><body></body></html>", "text/html", "euc-kr");
	    				showDialog(DIALOG_CARDAPP);
				        return false;
	    			}
	    			*/
                /**
                 * > 현대카드 안심클릭 droidxantivirusweb://
                 * - 백신앱 : Droid-x 안드로이이드백신 - NSHC
                 * - package name : net.nshc.droidxantivirus
                 * - 특이사항 : 백신 설치 유무는 체크를 하고, 없을때 구글마켓으로 이동한다는 이벤트는 있지만, 구글마켓으로 이동되지는 않음
                 * - 처리로직 : intent.getDataString()로 하여 droidxantivirusweb 값이 오면 현대카드 백신앱으로 인식하여
                 * 하드코딩된 마켓 URL로 이동하도록 한다.
                 *///현대카드 백신앱
                //하나SK카드 통합안심클릭앱
                //KB앱카드
                //롯데앱카드(간편결제)
                //롯데 모바일결제
                //삼성앱카드
                //신한앱카드
                //현대앱카드
            }

        } else {
            view.loadUrl(url)
            return false
        }

        return true
    }
}