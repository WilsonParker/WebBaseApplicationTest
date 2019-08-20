package co.kr.daisomall.push.interfaces

import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import retrofit2.Call
import retrofit2.http.GET


interface MobileManageable {

    @GET("/apps/getIntroImageUrl.php")
    fun getIntroImageUrl(): Call<HttpResultModel>
}