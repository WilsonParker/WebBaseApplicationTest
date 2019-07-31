package com.dev.hare.daisowebviewtest.push.interfaces

import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TokenManageable {

    @GET("/apps/updateDeviceToken.php")
    fun insertToken(@Query("device_token") token: String, @Query("device_type") os_type: String, @Query("device_id") device_id: String, @Query("api_token") api_key: String): Call<HttpResultModel>
}