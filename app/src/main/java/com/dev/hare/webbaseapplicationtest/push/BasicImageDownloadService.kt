package com.dev.hare.webbaseapplicationtest.push

import android.app.PendingIntent
import android.content.Context
import com.dev.hare.firebasepushmodule.basic.FirebaseBasicModel
import com.dev.hare.firebasepushmodule.models.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.models.NotificationDataModel
import com.dev.hare.firebasepushmodule.models.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.services.abstracts.images.AbstractWorkerImageDownloadService
import com.dev.hare.webbaseapplicationtest.activity.MainActivity
import kotlin.reflect.KClass

class BasicImageDownloadService : AbstractWorkerImageDownloadService<MainActivity>() {
    override val activityClass: KClass<MainActivity>
        get() = MainActivity::class
    override val _channelID = "WebBaseApplicationID"
    override val _channelName = "WebBaseApplication"

    override fun createModel(
        context: Context,
        dataModel: NotificationDataModel,
        builderModel: NotificationBuilderModel,
        pendingIntent: PendingIntent
    ): AbstractDefaultNotificationModel {
        return FirebaseBasicModel(context, dataModel, builderModel, pendingIntent)
    }
}