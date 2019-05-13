package com.dev.hare.firebasepushmodule.basic

import android.app.Activity
import com.dev.hare.firebasepushmodule.example.MainActivity
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService

class FirebaesBasicImageDownloadService : AbstractImageDownloadService() {
    override val activityCalss: Class<Activity>
        get() = MainActivity::class.java as Class<Activity>
    override val _channelID = "FirebasePushModule_ID"
    override val _channelName = "FirebasePushModule_Name"

}