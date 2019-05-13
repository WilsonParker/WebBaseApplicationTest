package com.dev.hare.firebasepushmodule.example

import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.Logger

object ExampleHttpService: AbstractCallService() {
    override val baseUrl: String
        get() = "http://enter6-api.dv9163.kro.kr:8001"

    override fun onInsertTokenSuccess(result: HttpResultModel?) {
        Logger.log(Logger.LogType.INFO, "token sequence : ${result.toString()}")
    }

    override fun onUpdateTokenWithUserCodeSuccess(result: HttpResultModel?) {
        Logger.log(Logger.LogType.INFO, "update is success? : ${result.toString()}")
    }

    override fun onUpdateTokenWithAgreementSuccess(result: HttpResultModel?) {
        Logger.log(Logger.LogType.INFO, "update is success? : ${result.toString()}")
    }
}