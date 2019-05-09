package com.dev.hare.firebasepushmodule.model.abstracts

import android.app.*
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.model.NotificationBigPictureStyleModel
import com.dev.hare.firebasepushmodule.model.NotificationBigTextStyleModel
import com.dev.hare.firebasepushmodule.model.NotificationBuilderModel
import com.dev.hare.firebasepushmodule.model.NotificationDataModel
import com.dev.hare.firebasepushmodule.model.interfaces.NotificationBuildable

abstract class AbstractDefaultNotificationModel(
    private val context: Context,
    private val builderModel: NotificationBuilderModel,
    private val pendingIntent: PendingIntent,
    var dataModel: NotificationDataModel,
    var textStyleModel: NotificationBigTextStyleModel,
    var pictureStyleModel: NotificationBigPictureStyleModel
) : NotificationBuildable {

    enum class Key(key: String) {
        TITLE("title"),
        CONTENT("content"),
        IMAGE_URL("imageUrl"),
        LINK("link"),
        PUSH_TYPE("push_type")
    }

    enum class PushType(type: String) {
        TXT("txt"),
        IMG("img"),
        NOTI_IMG("noti_img")
    }

    protected var notificationManager: NotificationManager =
        context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager

    var img: Bitmap? = null
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
   /* protected var pendingIntent: PendingIntent? = null
        get() {
            if (field == null) field = createPendingIntent(MainActivity::class.java)
            return field
        }*/

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
        return NotificationChannel(dataModel.channelID, dataModel.channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            enableLights(true)
            enableVibration(true)
            importance = NotificationManager.IMPORTANCE_DEFAULT
        }
    }

    /**
     * create PendingIntent by default
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 28/03/2019
     * */
    /*override fun createDefaultPendingIntent(activity: Class<out Activity>): PendingIntent {
        var intent = Intent(context, activity)
        return PendingIntent.getActivity(
            context,
            _REQUEST_CODE,
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }*/

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
        applyNotificationBuilderStyle(PushType.valueOf(dataModel.pushType!!))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(0, builderModel.builder?.build())
        } else {
            notificationManager.notify(0, builderModel.compatBuilder?.build())
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
    private fun applyNotificationBuilderStyle(type: AbstractDefaultNotificationModel.PushType) {
        when (type) {
            AbstractDefaultNotificationModel.PushType.TXT -> {
                NotificationCompat.BigTextStyle(builderModel.compatBuilder).run {
                    textStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                    textStyleModel.bigText?.let { bigText(it) }
                    textStyleModel.summaryText?.let { setSummaryText(it) }
                    builderModel.compatBuilder?.setStyle(this)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    var textStyle = Notification.BigTextStyle().apply {
                        textStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                        textStyleModel.bigText?.let { bigText(it) }
                        textStyleModel.summaryText?.let { setSummaryText(it) }
                    }
                    builderModel.builder?.style = textStyle
                }
            }

            PushType.IMG, PushType.NOTI_IMG -> {
                NotificationCompat.BigPictureStyle(builderModel.compatBuilder).run {
                    pictureStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                    pictureStyleModel.bigPicture?.let { bigPicture(it) }
                    pictureStyleModel.bigLargeIcon?.let { bigLargeIcon(it) }
                    if (type == PushType.NOTI_IMG) {
                        pictureStyleModel.summaryText?.let { setSummaryText(it) }
                    }
                    builderModel.compatBuilder?.setStyle(this)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    var pictureStyle = Notification.BigPictureStyle().apply {
                        pictureStyleModel.bigContentTitle?.let { setBigContentTitle(it) }
                        pictureStyleModel.bigLargeIcon?.let { bigLargeIcon(it) }
                        pictureStyleModel.bigPicture?.let { bigPicture(it) }
                        if (type == PushType.NOTI_IMG) {
                            pictureStyleModel.summaryText?.let { setSummaryText(it) }
                        }
                    }
                    builderModel.builder?.style = pictureStyle
                }
            }
        }
    }

}