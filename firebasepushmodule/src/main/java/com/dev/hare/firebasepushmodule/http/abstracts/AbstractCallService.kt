package com.dev.hare.firebasepushmodule.http.abstracts

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class AbstractCallService<Service> {
    abstract val baseUrl: String
    protected abstract val retrofitCallableClass: Class<Service>
    protected val retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(GsonConverterFactory.create())
    }.build()
    protected val pushService: Service = retrofit.create(retrofitCallableClass)

}