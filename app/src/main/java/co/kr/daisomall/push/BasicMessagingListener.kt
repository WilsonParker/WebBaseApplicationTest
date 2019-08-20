package co.kr.daisomall.push

import co.kr.daisomall.activity.MainWithIntroActivity
import com.dev.hare.firebasepushmodule.services.abstracts.AbstractFirebaseMessagingForegroundService

class BasicMessagingListener: AbstractFirebaseMessagingForegroundService<MainWithIntroActivity, ImageDownloadService>() {
    override val serviceClass = ImageDownloadService::class
}