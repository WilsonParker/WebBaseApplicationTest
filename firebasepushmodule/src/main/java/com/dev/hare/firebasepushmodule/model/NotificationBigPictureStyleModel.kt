package com.dev.hare.firebasepushmodule.model

import android.graphics.Bitmap
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractNotificationBigStyleModel

class NotificationBigPictureStyleModel : AbstractNotificationBigStyleModel() {
    private var mBigLargeIconSet: Boolean = false
    var bigPicture: Bitmap? = null
    var bigLargeIcon: Bitmap? = null
        set(value) {
            this.mBigLargeIconSet = true
        }
}