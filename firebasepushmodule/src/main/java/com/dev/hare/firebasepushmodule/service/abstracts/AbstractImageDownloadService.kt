package com.dev.hare.firebasepushmodule.service.abstracts

import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.model.interfaces.NotificationBuildable
import com.dev.hare.firebasepushmodule.util.ImageUtilUsingThread
import com.dev.hare.firebasepushmodule.util.Logger
import com.google.firebase.messaging.RemoteMessage
import java.lang.NullPointerException

abstract class AbstractImageDownloadService : Service() {
    companion object {
        const val KEY_URL = AbstractDefaultNotificationModel.Key.IMAGE_URL
        const val KEY_REMOTE_MESSAGE = "msg"
        const val _REQUEST_CODE = 0
        private const val CHANNEL_ID= 1
    }


    var model: AbstractDefaultNotificationModel? = null
    protected var url: String? = null
    protected var data: Map<String, String>? = null
    protected var onImageLoadCompleteListener: ImageUtilUsingThread.OnImageLoadCompleteListener = createOnImageLoadCompleteListener()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        runAfterBind(intent)
        return Service.START_REDELIVER_INTENT
    }

    /**
     * RemoteMessage 를 이용해서 imageUrl, data property 에 값을 대입 한 후 run() 을 실행합니다
     *
     * @Author : Hare
     * @Update : 19.3.27
     */
    private fun runAfterBind(intent: Intent?) {
        intent?.let {
            url = it.getStringExtra(KEY_URL)
            data = (it.getParcelableExtra(KEY_REMOTE_MESSAGE) as RemoteMessage).data
            model = createNotificationModel(data)
            run()
        }
    }

    /**
     * Oreo 버전 이후 부터는 빈 Notification 을 사용할 수 없기 때문에
     * 새로운 Notification 을 생성하여 실행해야 합니다
     *
     * @Author : Hare
     * @Update : 19.3.27
     */
    @Throws(NullPointerException::class)
    protected fun notifyOwn(model: AbstractDefaultNotificationModel?) {
        if(model == null)
            throw NullPointerException("NotificationBuildable is null")
        startForeground(CHANNEL_ID,
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                model.ownNotification
            else
                Notification()
        )
    }

    protected fun notifyStop() {
        stopForeground(true)
        stopSelf(CHANNEL_ID)
    }

    /**
     * Foreground 로 Notification 을 실행하고 푸시를 보냅니다
     *
     * @Author : Hare
     * @Update : 19.3.27
     */
    protected fun run() {
        try {
            notifyOwn(model)
            model!!.let {
                val imageUtilUsingThread = ImageUtilUsingThread()
                imageUtilUsingThread.urlToBitmapUsingThread(
                    url,
                    null,
                    onImageLoadCompleteListener
                )
            }
        } catch (e: Exception) {
            Logger.log(Logger.LogType.ERROR, e)
        }
    }

    protected abstract fun createNotificationModel(data: Map<String, String>?): AbstractDefaultNotificationModel

    protected abstract fun createOnImageLoadCompleteListener(): ImageUtilUsingThread.OnImageLoadCompleteListener

    /**
     * create PendingIntent by default
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 09/05/2019
     * */
    fun createDefaultPendingIntent(activity: Class<out Activity>): PendingIntent {
        var intent = Intent(this, activity)
        return PendingIntent.getActivity(
            this,
            _REQUEST_CODE,
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
