package com.dev.hare.webbaseapplicationtest.push

import com.dev.hare.firebasepushmodule.basic.AbstractTokenCallService
import com.dev.hare.webbaseapplicationtest.constants.APP_API_URL

object BasicTokenCallService : AbstractTokenCallService() {
    override val baseUrl: String
        get() = APP_API_URL
}
