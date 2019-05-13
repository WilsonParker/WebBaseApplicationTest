package com.dev.hare.firebasepushmodule.model

import android.content.Context
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractNotificationBigStyleModel

class NotificationDataModel(
    val context: Context,
    val channelID: String = "channelID",
    val channelName: String = "channelName",
    data: Map<String, String>? = null
) {

    var title: String? = null
    var content: String? = null
    var link: String? = null
    var imageUrl: String? = null
    var pushType: String? = null
    var image: Bitmap? = null
    var largeIcon: Bitmap? = null

    init {
        data?.let {
            bindData(it)
        }
    }

    fun bindData(data: Map<String, String>) {
        this.title = data[AbstractDefaultNotificationModel.Key.TITLE]
        this.content = data[AbstractDefaultNotificationModel.Key.CONTENT]
        this.imageUrl = data[AbstractDefaultNotificationModel.Key.IMAGE_URL]
        this.link = data[AbstractDefaultNotificationModel.Key.LINK]
        this.pushType = data[AbstractDefaultNotificationModel.Key.PUSH_TYPE]
    }

}