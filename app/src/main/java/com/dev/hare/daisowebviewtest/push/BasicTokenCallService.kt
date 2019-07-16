package com.dev.hare.daisowebviewtest.push

import com.dev.hare.daisowebviewtest.constants.API_KEY
import com.dev.hare.daisowebviewtest.constants.APP_API_URL
import com.dev.hare.firebasepushmodule.basic.AbstractTokenCallService

object BasicTokenCallService : AbstractTokenCallService() {
    override val key: String = API_KEY
    override val baseUrl: String
        get() = APP_API_URL
}
