package com.highline.tramservice.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.highline.tramservice.helper.Constants
import com.highline.tramservice.model.DeviceToken
import com.highline.tramservice.model.RoutesInfo
import com.highline.tramservice.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyTramViewModel : ViewModel() {

    private val TAG: String = "MyTramViewModel"

    /**
     * Initializes
     */
    private val deviceToken: MutableLiveData<DeviceToken> by lazy {
        MutableLiveData<DeviceToken>().also {
            fetchDeviceToken()
        }
    }

    var applicationState: MutableLiveData<Constants.APP_STATE> =
        MutableLiveData<Constants.APP_STATE>()
    var northBoundTramRouteInfo: MutableLiveData<RoutesInfo> = MutableLiveData<RoutesInfo>()
    var southBoundTramRouteInfo: MutableLiveData<RoutesInfo> = MutableLiveData<RoutesInfo>()
    var dataNotFoundErrorMessage: MutableLiveData<String> = MutableLiveData<String>()

    // Clear Handler
    val onClickClearLiveData: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    fun onClickClearLiveData() = onClickClearLiveData.postValue(Unit)

    // Refresh handler
    val onClickRefreshLiveData: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    fun onClickRefreshLiveData() = onClickRefreshLiveData.postValue(Unit)

    /**
     * Function returns Device Token from View Model
     */
    fun getDeviceToken(): LiveData<DeviceToken> {
        return deviceToken
    }

    /**
     * Asynchronously get Device Token from specified URL
     */
    private fun fetchDeviceToken() {
        val networkService: ApiInterface = ApiInterface.create()
        val call: Call<DeviceToken> = networkService.getDeviceToken("TTIOSJSON", "HomeTime")
        applicationState.postValue(Constants.APP_STATE.REQUEST_SENT)
        with(call) {
            enqueue(object : Callback<DeviceToken> {
                override fun onResponse(call: Call<DeviceToken>, response: Response<DeviceToken>) {
                    if (response.code() == 200) {
                        deviceToken.postValue(response.body())
                        applicationState.postValue(Constants.APP_STATE.RESPONSE_SUCCESS)
                    } else {
                        deviceToken.postValue(null)
                        applicationState.postValue(Constants.APP_STATE.RESPONSE_FAILED)
                    }
                }

                override fun onFailure(call: Call<DeviceToken>, t: Throwable) {
                    deviceToken.postValue(null)
                    applicationState.postValue(Constants.APP_STATE.RESPONSE_FAILED)
                }
            })
        }
    }

    /**
     * Calls Retrofit service to fetch the data
     */
    fun getTramRouteInfo(stopId: Int, deviceToken: String) {
        val networkService: ApiInterface = ApiInterface.create()
        val call: Call<RoutesInfo> =
            networkService.getTramRoutesInfo(stopId, "TTIOSJSON", 2, deviceToken)
        applicationState.postValue(Constants.APP_STATE.REQUEST_SENT)
        with(call) {
            enqueue(object : Callback<RoutesInfo> {
                override fun onResponse(call: Call<RoutesInfo>, response: Response<RoutesInfo>) {
                    if (response.code() == 200) {
                        with(response.body()) {
                            when (stopId) {
                                Constants.NORTH_BOUND_STOPID -> {
                                    this?.stopId = stopId
                                    northBoundTramRouteInfo.postValue(this)
                                    applicationState.postValue(Constants.APP_STATE.RESPONSE_SUCCESS01)
                                }
                                Constants.SOUTH_BOUND_STOPID -> {
                                    this?.stopId = stopId
                                    southBoundTramRouteInfo.postValue(this)
                                    applicationState.postValue(Constants.APP_STATE.RESPONSE_SUCCESS02)
                                }
                            }
                        }
                    } else {
                        dataNotFoundErrorMessage.postValue("Data not found")
                        applicationState.postValue(Constants.APP_STATE.RESPONSE_FAILED)
                    }
                }

                override fun onFailure(call: Call<RoutesInfo>, t: Throwable) {
                    dataNotFoundErrorMessage.postValue(t.localizedMessage)
                    applicationState.postValue(Constants.APP_STATE.RESPONSE_FAILED)
                }
            })
        }
    }
}
