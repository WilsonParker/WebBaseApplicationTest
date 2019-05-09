package com.dev.hare.firebasepushmodule.http.abstracts

import com.dev.hare.firebasepushmodule.http.interfaces.PushCallable
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class AbstractCallService {
    abstract val baseUrl: String
    protected val retrofitCallableClass = PushCallable::class.java
    val pushService: PushCallable

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        pushService = retrofit.create(retrofitCallableClass)
    }

    fun insertToken(token: String, device_id: String) {
        pushService.insertToken(token, "a", device_id).enqueue(object : Callback<HttpResultModel> {
            override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                if (response.isSuccessful) {
                    var result: HttpResultModel? = response.body()
                    try {
                        HttpConstantModel.token_sequence = Integer.parseInt(result?.message)
                        onInsertTokenSuccess(result)
                    } catch (e: NumberFormatException) {
                        Logger.log(Logger.LogType.ERROR, e)
                    }
                }
            }

            override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                Logger.log(Logger.LogType.ERROR, t)
            }
        })
    }

    fun updateTokenWithUserCode(user_code: String) {
        pushService.updateTokenWithUserCode(HttpConstantModel.token_sequence, user_code)
            .enqueue(object : Callback<HttpResultModel> {
                override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                    if (response.isSuccessful) {
                        var result: HttpResultModel? = response.body()
                        onUpdateTokenWithUserCodeSuccess(result)
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, t)
                }
            })
    }

    fun updateTokenWithAgreement(agreement: Boolean) {
        pushService.updateTokenWithAgreement(HttpConstantModel.token_sequence, agreement)
            .enqueue(object : Callback<HttpResultModel> {
                override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                    if (response.isSuccessful) {
                        var result: HttpResultModel? = response.body()
                        onUpdateTokenWithAgreementSuccess(result)
                    }
                }

                override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                    Logger.log(Logger.LogType.ERROR, t)
                }
            })
    }

    abstract fun onInsertTokenSuccess(result: HttpResultModel?)
    abstract fun onUpdateTokenWithUserCodeSuccess(result: HttpResultModel?)
    abstract fun onUpdateTokenWithAgreementSuccess(result: HttpResultModel?)

}