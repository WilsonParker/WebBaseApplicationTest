package com.dev.hare.firebasepushmodule.sample

import android.app.*
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.R
import com.dev.hare.firebasepushmodule.model.NotificationBigPictureStyleModel
import com.dev.hare.firebasepushmodule.model.NotificationBigTextStyleModel
import com.dev.hare.firebasepushmodule.model.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.model.NotificationDataModel
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel

class FCMModel(
    context: Context,
    dataModel: NotificationDataModel,
    builderModel: NotificationBuilderModel,
    pendingIntent: PendingIntent
) :
    AbstractDefaultNotificationModel(
        context,
        builderModel,
        pendingIntent,
        dataModel,
        NotificationBigTextStyleModel().apply {
            bigContentTitle = dataModel.title
            bigText = dataModel.content
            summaryText = dataModel.content
        },
        NotificationBigPictureStyleModel().apply {
            bigContentTitle = dataModel.title
            bigPicture =  dataModel.image
            summaryText = dataModel.content
        }
    ) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createOwnNotification(): Notification {
        return createDefaultOwnNotification()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        return createDefaultNotificationChannel(notificationManager)
    }

}