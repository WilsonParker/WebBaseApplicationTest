package com.dev.hare.firebasepushmodule.basic

import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.interfaces.TokenManageable
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.hareutilitymodule.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KClass

abstract class AbstractTokenCallService : AbstractCallService<TokenManageable>() {
    override val retrofitCallableClass: KClass<TokenManageable>
        get() = TokenManageable::class

    fun insertToken(token: String, device_id: String, callback: (result: HttpResultModel?) -> Unit) {
        pushService.insertToken(token, "a", device_id).enqueue(object : Callback<HttpResultModel> {
            override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                var result: HttpResultModel? = response.body()
                if (response.isSuccessful) {
                    try {
                        HttpConstantModel.token_sequence = Integer.parseInt(result?.data?.get("sequence"))
                        callback(result)
                        result?.let {
                            Logger.log(Logger.LogType.INFO, "insertToken", it.toString())
                        }
                    } catch (e: NumberFormatException) {
                        Logger.log(Logger.LogType.ERROR, "insertToken", e)
                    }
                } else {
                    Logger.log(Logger.LogType.ERROR, "insertToken is not successful")
                }
            }

            override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                Logger.log(Logger.LogType.ERROR, "insertToken", t)
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
                    } else {
                        Logger.log(Logger.LogType.ERROR, "updateTokenWithUserCode is not successful")
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, "updateTokenWithUserCode", t)
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
                    } else {
                        Logger.log(Logger.LogType.ERROR, "updateTokenWithUser is not successful")
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, "updateTokenWithUser", t)
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
                    } else {
                        Logger.log(Logger.LogType.ERROR, "updateTokenWithAgreement is not successful")
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, "updateTokenWithAgreement", t)
                }
            })
    }

}
