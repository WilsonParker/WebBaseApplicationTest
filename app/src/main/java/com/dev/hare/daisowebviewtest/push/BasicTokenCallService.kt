package com.dev.hare.daisowebviewtest.push

import android.content.Context
import com.dev.hare.apputilitymodule.util.Logger
import com.dev.hare.daisowebviewtest.constants.API_KEY
import com.dev.hare.daisowebviewtest.constants.APP_API_URL
import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.interfaces.TokenManageable
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KClass

object BasicTokenCallService : AbstractCallService<TokenManageable>() {
    val key: String = API_KEY
    override val baseUrl: String
        get() = APP_API_URL

    override val retrofitCallableClass: KClass<TokenManageable>
        get() = TokenManageable::class

    fun insertToken(context: Context, token: String, device_id: String, callback: (result: HttpResultModel?) -> Unit) {
        pushService.insertToken(token, "a", device_id, key).enqueue(object : Callback<HttpResultModel> {
            override fun onResponse(call: Call<HttpResultModel>, response: Response<HttpResultModel>) {
                var result: HttpResultModel? = response.body()
                if (response.isSuccessful) {
                    try {
                        var sequence = Integer.parseInt(result?.data?.get("sequence"))
                        HttpConstantModel.token_sequence = sequence
                        FirebaseUtil.setTokenSequence(sequence)
                        callback(result)
                    } catch (e: NumberFormatException) {
                        Logger.log(Logger.LogType.ERROR, "insertToken", e)
                    }
                } else {
                    Logger.log(Logger.LogType.ERROR, "insertToken is not successful", response.message(), response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<HttpResultModel>, t: Throwable) {
                Logger.log(Logger.LogType.ERROR, "insertToken", t)
            }
        })
    }
}
