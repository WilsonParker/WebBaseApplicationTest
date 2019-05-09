package com.dev.hare.firebasepushmodule.service.abstracts

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

abstract class AbstractFirebaseMessagingServive : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage != null && remoteMessage.data != null && !remoteMessage.data.isEmpty()) {
            run(remoteMessage)
        }
    }

    protected abstract fun run(remoteMessage: RemoteMessage)

    /**
     * Intent 를 create, setting 합니다
     *
     * @Author : Hare
     * @Updated : 19.3.27
     */
    protected abstract fun createIntent(remoteMessage: RemoteMessage) : Intent

}