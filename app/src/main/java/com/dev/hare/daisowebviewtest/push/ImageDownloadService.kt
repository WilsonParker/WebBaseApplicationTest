package com.dev.hare.daisowebviewtest.push

import android.app.PendingIntent
import android.content.Context
import com.dev.hare.daisowebviewtest.R
import com.dev.hare.daisowebviewtest.activity.MainWithIntroActivity
import com.dev.hare.firebasepushmodule.basic.FirebaseBasicModel
import com.dev.hare.firebasepushmodule.models.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.models.NotificationDataModel
import com.dev.hare.firebasepushmodule.models.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.services.abstracts.images.AbstractForegroundImageDownloadService
import kotlin.reflect.KClass

class ImageDownloadService : AbstractForegroundImageDownloadService<MainWithIntroActivity>() {
    override val icon = R.drawable.appicon
    override val activityClass: KClass<MainWithIntroActivity>
        get() = MainWithIntroActivity::class
    override val _channelID = "Enter6Channel"
    override val _channelName = "Enter6"

    override fun createModel(
        context: Context,
        dataModel: NotificationDataModel,
        builderModel: NotificationBuilderModel,
        pendingIntent: PendingIntent
    ): AbstractDefaultNotificationModel {
        return FirebaseBasicModel(context, dataModel, builderModel, pendingIntent)
    }
}