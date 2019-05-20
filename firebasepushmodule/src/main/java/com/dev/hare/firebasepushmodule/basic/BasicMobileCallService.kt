package com.dev.hare.firebasepushmodule.basic

import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.interfaces.MobileManageable
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BasicMobileCallService : AbstractCallService<MobileManageable>() {
    override val retrofitCallableClass: Class<MobileManageable>
        get() = MobileManageable::class.java
    override val baseUrl: String
        get() = "http://enter6-admin-api.dv9163.kro.kr:8001"

    fun getIntroImageUrl(callback: (result: HttpResultModel?) -> Unit) {
        pushService.getIntroImageUrl().enqueue(object : Callback<HttpResultModel> {
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