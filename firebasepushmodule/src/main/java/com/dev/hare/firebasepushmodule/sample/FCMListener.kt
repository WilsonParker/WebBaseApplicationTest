package com.dev.hare.firebasepushmodule.sample

import android.content.Intent
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractFirebaseMessagingForegroundService
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService
import com.google.firebase.messaging.RemoteMessage


class FCMListener: AbstractFirebaseMessagingForegroundService() {

    override fun createIntent(remoteMessage: RemoteMessage): Intent {
        val intent = Intent()
        intent.setClass(baseContext, FCMService::class.java)
        intent.putExtra(AbstractImageDownloadService.KEY_URL, remoteMessage.data[AbstractImageDownloadService.KEY_URL])
        intent.putExtra(AbstractImageDownloadService.KEY_REMOTE_MESSAGE, remoteMessage)
        return intent
    }

}