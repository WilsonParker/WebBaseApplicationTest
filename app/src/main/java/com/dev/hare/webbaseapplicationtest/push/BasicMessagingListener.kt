package com.dev.hare.webbaseapplicationtest.push

import com.dev.hare.firebasepushmodule.service.abstracts.AbstractFirebaseMessagingBackgroundService
import com.dev.hare.webbaseapplicationtest.activity.MainActivity

class BasicMessagingListener: AbstractFirebaseMessagingBackgroundService<MainActivity, BasicImageDownloadService>() {
    override val serviceClass = BasicImageDownloadService::class
}