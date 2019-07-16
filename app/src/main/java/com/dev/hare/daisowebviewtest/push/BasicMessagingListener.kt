package com.dev.hare.daisowebviewtest.push

import com.dev.hare.daisowebviewtest.activity.MainWithIntroActivity
import com.dev.hare.firebasepushmodule.services.abstracts.AbstractFirebaseMessagingForegroundService

class BasicMessagingListener: AbstractFirebaseMessagingForegroundService<MainWithIntroActivity, ImageDownloadService>() {
    override val serviceClass = ImageDownloadService::class
}