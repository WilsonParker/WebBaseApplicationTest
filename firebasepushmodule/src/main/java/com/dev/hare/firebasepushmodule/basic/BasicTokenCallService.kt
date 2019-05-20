package com.dev.hare.firebasepushmodule.basic

import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.interfaces.TokenManageable
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BasicTokenCallService : AbstractCallService<TokenManageable>() {
    override val retrofitCallableClass: Class<TokenManageable>
        get() = TokenManageable::class.java
    override val baseUrl: String
        get() = "http://enter6-api.dv9163.kro.kr:8001"

    fun insertToken(token: String, device_id: String, callback: (result: HttpResultModel?) -> Unit) {
        pushService.insertToken(token, "a", device_id).enqueue(object : Callback<HttpResultModel> {
            override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                var result: HttpResultModel? = response.body()
                if (response.isSuccessful) {
                    try {
                        HttpConstantModel.token_sequence = Integer.parseInt(result?.data?.get("sequence"))
                        callback(result)
                    } catch (e: NumberFormatException) {
                        Logger.log(Logger.LogType.ERROR, "", e)
                    }
                }
            }

            override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                Logger.log(Logger.LogType.ERROR, "", t)
            }
        })
    }

    fun updateTokenWithUserCode(user_code: String, callback: (result: HttpResultModel?) -> Unit) {
        pushService.updateTokenWithUserCode(HttpConstantModel.token_sequence, user_code)
            .enqueue(object : Callback<HttpResultModel> {
                override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                    var result: HttpResultModel? = response.body()
                    if (response.isSuccessful) {
                        callback(result)
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, "", t)
                }
            })
    }

    fun updateTokenWithUser(callback: (result: HttpResultModel?) -> Unit) {
        pushService.updateTokenWithUser(HttpConstantModel.token_sequence)
            .enqueue(object : Callback<HttpResultModel> {
                override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                    var result: HttpResultModel? = response.body()
                    if (response.isSuccessful) {
                        callback(result)
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, "", t)
                }
            })
    }

    fun updateTokenWithAgreement(agreement: Boolean, callback: (result: HttpResultModel?) -> Unit) {
        pushService.updateTokenWithAgreement(HttpConstantModel.token_sequence, agreement)
            .enqueue(object : Callback<HttpResultModel> {
                override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                    var result: HttpResultModel? = response.body()
                    if (response.isSuccessful) {
                        callback(result)
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, "", t)
                }
            })
    }

}
