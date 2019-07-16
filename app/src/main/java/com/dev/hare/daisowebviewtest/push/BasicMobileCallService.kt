package com.dev.hare.daisowebviewtest.push

import com.dev.hare.daisowebviewtest.constants.ADMIN_API_URL
import com.dev.hare.firebasepushmodule.basic.AbstractMobileCallService

object BasicMobileCallService : AbstractMobileCallService() {
    override val baseUrl: String
        get() = ADMIN_API_URL
}