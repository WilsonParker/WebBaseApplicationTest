package co.kr.daisomall.push.interfaces

import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TokenManageable {

    @GET("/apps/updateDeviceToken.php")
    fun insertToken(@Query("device_token") token: String, @Query("device_type") os_type: String, @Query("device_id") device_id: String, @Query("api_token") api_key: String): Call<HttpResultModel>

    /**
     * Push service setting call.
     *
     * @param receiveKey the receive key
     * @param deviceId   the device id
     * @return the call
     */
    @GET("admin/mobile/appapi/app/server2_https.php?act=setPushService&settype=insert&os_type=a")
    fun pushServiceSetting(@Query("receive_key") receiveKey: String, @Query("device_id") deviceId: String): Call<JsonObject>
}