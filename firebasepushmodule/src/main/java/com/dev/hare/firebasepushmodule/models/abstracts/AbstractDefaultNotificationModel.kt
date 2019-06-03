package com.dev.hare.firebasepushmodule.models.abstracts

import android.app.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dev.hare.firebasepushmodule.models.NotificationBigPictureStyleModel
import com.dev.hare.firebasepushmodule.models.NotificationBigTextStyleModel
import com.dev.hare.firebasepushmodule.models.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.models.NotificationDataModel
import com.dev.hare.firebasepushmodule.models.interfaces.NotificationBuildable
import com.dev.hare.hareutilitymodule.util.Logger

abstract class AbstractDefaultNotificationModel(
    private val context: Context,
    private val builderModel: NotificationBuilderModel,
    private val pendingIntent: PendingIntent,
    var dataModel: NotificationDataModel,
    var textStyleModel: NotificationBigTextStyleModel,
    var pictureStyleModel: NotificationBigPictureStyleModel
) : NotificationBuildable {

    class Key {
        companion object {
            const val TITLE = "title"
            const val CONTENT = "content"
            const val IMAGE_URL = "image_url"
            const val LINK = "link"
            const val PUSH_TYPE = "push_type"
        }
    }

    enum class PushType(val type: String) {
        TXT("txt"),
        IMG("img"),
        NOTI_IMG("noti_img")
    }

    protected var notificationManager: NotificationManager =
        context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager

    var ownNotification: Notification? = null
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            notificationManager.createNotificationChannel(notificationChannel)
            if (field == null) field = createOwnNotification()
            return field
        }
    protected var notificationChannel: NotificationChannel? = null
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            if (field == null) field = createNotificationChannel(notificationManager)
            return field
        }

    /**
     * create default foreground Notification
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 09/05/2019
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createDefaultOwnNotification(): Notification {
        createDefaultNotificationChannel(notificationManager)
        return Notification.Builder(context, dataModel.channelID).apply {
            setOngoing(true)
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(pendingIntent)
        }.build()
    }

    /**
     * create NotificationChannel in NotificationManager
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 28/03/2019
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createDefaultNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        return NotificationChannel(
            dataModel.channelID,
            dataModel.channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            enableVibration(true)
            importance = NotificationManager.IMPORTANCE_DEFAULT
        }
    }

    /**
     * notify
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 09/05/2019
     * */
    fun runNotification() {
        try {
            var pushType = PushType.valueOf(dataModel.pushType!!.toUpperCase())
            applyNotificationBuilderStyle(pushType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(notificationChannel)
                notificationManager.notify(0, builderModel.builder?.build())
            } else {
                notificationManager.notify(0, builderModel.compatBuilder?.build())
            }
        } catch (e: Exception) {
            Logger.log(Logger.LogType.ERROR, "", e)
        }
    }

    /**
     * apply Style to NotificationCompat.Builder & Notification.Builder
     *
     * @param
     * @return
     * @author Hare
     * @added 09/05/2019
     * @updated 09/05/2019
     * */
    private fun applyNotificationBuilderStyle(type: PushType) {
        when (type) {
            PushType.TXT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builderModel.builder?.style = Notification.BigTextStyle().apply {
                        textStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                        textStyleModel.bigText?.let { bigText(it) }
                        textStyleModel.summaryText?.let { setSummaryText(it) }
                    }
                } else {
                    NotificationCompat.BigTextStyle(builderModel.compatBuilder).run {
                        textStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                        textStyleModel.bigText?.let { bigText(it) }
                        textStyleModel.summaryText?.let { setSummaryText(it) }
                        builderModel.compatBuilder?.setStyle(this)
                    }
                }
            }

            PushType.IMG, PushType.NOTI_IMG -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builderModel.builder?.style = Notification.BigPictureStyle().apply {
                        pictureStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                        pictureStyleModel.bigLargeIcon?.let { bigLargeIcon(it) }
                        pictureStyleModel.bigPicture?.let { bigPicture(it) }
                        if (type == PushType.NOTI_IMG) {
                            pictureStyleModel.summaryText?.let { setSummaryText(it) }
                        }
                    }
                } else {
                    NotificationCompat.BigPictureStyle(builderModel.compatBuilder).run {
                        pictureStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                        pictureStyleModel.bigPicture?.let { bigPicture(it) }
                        pictureStyleModel.bigLargeIcon?.let { bigLargeIcon(it) }
                        if (type == PushType.NOTI_IMG) {
                            pictureStyleModel.summaryText?.let { setSummaryText(it) }
                        }
                        builderModel.compatBuilder?.setStyle(this)
                    }
                }
            }
        }
    }

}