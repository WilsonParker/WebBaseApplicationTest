package com.dev.hare.firebasepushmodule.sample

import android.app.Notification
import android.graphics.Bitmap
import com.dev.hare.firebasepushmodule.R
import com.dev.hare.firebasepushmodule.model.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.model.NotificationDataModel
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.sample.FCMModel
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService
import com.dev.hare.firebasepushmodule.util.ImageUtilUsingThread

class FCMService : AbstractImageDownloadService() {
    private val _channelID = "FirebasePushModule_ID"
    private val _channelName = "FirebasePushModule_Name"

    override fun createNotificationModel(data: Map<String, String>?): AbstractDefaultNotificationModel {
        var pendingIntent = createDefaultPendingIntent(MainActivity::class.java)
        var dataModel = NotificationDataModel(this, _channelID, _channelName, data)
        var builderModel = NotificationBuilderModel(this, _channelID).apply {
            // dataModel.title?.let { setContentTitle(it) }
            // dataModel.content?.let { setContentText(it) }
            // setSubText()
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setPriority(Notification.PRIORITY_MAX)
            setContentIntent(pendingIntent)
        }
        return FCMModel(this, dataModel, builderModel, pendingIntent)
    }

    override fun createOnImageLoadCompleteListener(): ImageUtilUsingThread.OnImageLoadCompleteListener {
        return object : ImageUtilUsingThread.OnImageLoadCompleteListener {
            override fun onComplete(bitmap: Bitmap?) {
                model?.run {
                    pictureStyleModel.bigPicture = bitmap
                    runNotification()
                    notifyStop()
                }
            }
        }
    }
}