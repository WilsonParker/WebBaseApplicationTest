package co.kr.daisomall.push

import android.content.Context
import co.kr.daisomall.constants.Constants.API_KEY
import co.kr.daisomall.constants.Constants.APP_API_URL
import co.kr.daisomall.push.interfaces.TokenManageable
import com.dev.hare.apputilitymodule.util.Logger
import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.webbasetemplatemodule.util.CommonUtil
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
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

    /**
     * Convert Daiso Java function
     *
     * @param
     * @return
     * @author      Hare
     * @added       2019-08-20
     * @updated     2019-08-20
     * */
    fun tokenRefresh(context: Context) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val token = instanceIdResult.token
            var mToken = HttpConstantModel.token
            if ("" == mToken || token != mToken) {
                try {
                    val call = pushService.pushServiceSetting(token, CommonUtil.getDeviceUUID(context))
                    call.enqueue(object : Callback<JsonObject> {

                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            val respObj = response.body()
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        }
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}