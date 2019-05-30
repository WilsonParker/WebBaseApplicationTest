package com.dev.hare.webbaseapplicationtest.push

import com.dev.hare.firebasepushmodule.basic.AbstractMobileCallService
import com.dev.hare.webbaseapplicationtest.constants.ADMIN_API_URL

object BasicMobileCallService : AbstractMobileCallService() {
    override val baseUrl: String
        get() = ADMIN_API_URL
}