package co.kr.daisomall.push

import com.dev.hare.apputilitymodule.util.Logger
import co.kr.daisomall.constants.Constants.API_KEY
import co.kr.daisomall.constants.Constants.APP_API_URL
import co.kr.daisomall.push.interfaces.MobileManageable
import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KClass

object BasicMobileCallService : AbstractCallService<MobileManageable>() {
    val key: String = API_KEY
    override val baseUrl: String
        get() = APP_API_URL
    override val retrofitCallableClass: KClass<MobileManageable>
        get() = MobileManageable::class

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