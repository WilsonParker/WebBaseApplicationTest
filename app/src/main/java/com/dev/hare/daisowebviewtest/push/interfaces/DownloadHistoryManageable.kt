package com.dev.hare.daisowebviewtest.push.interfaces

import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface DownloadHistoryManageable {

    @GET("/apps/insertDownloadHistory")
    fun insertDownloadHistory(@Query("os_type") os_type: String, @Query("api_key") api_key: String): Call<HttpResultModel>
}