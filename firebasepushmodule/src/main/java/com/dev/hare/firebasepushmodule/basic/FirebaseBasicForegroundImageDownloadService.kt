package com.dev.hare.firebasepushmodule.basic

import android.app.PendingIntent
import android.content.Context
import com.dev.hare.firebasepushmodule.example.ExampleMainActivity
import com.dev.hare.firebasepushmodule.model.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.model.NotificationDataModel
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.service.abstracts.images.AbstractForegroundImageDownloadService
import kotlin.reflect.KClass

class FirebaseBasicForegroundImageDownloadService : AbstractForegroundImageDownloadService<ExampleMainActivity>() {
    override val activityClass: KClass<ExampleMainActivity>
        get() = ExampleMainActivity::class
    override val _channelID = "FirebasePushModule_ID"
    override val _channelName = "FirebasePushModule_Name"

    override fun createModel(
        context: Context,
        dataModel: NotificationDataModel,
        builderModel: NotificationBuilderModel,
        pendingIntent: PendingIntent
    ): AbstractDefaultNotificationModel {
        return FirebaseBasicModel(context, dataModel, builderModel, pendingIntent)
    }
}