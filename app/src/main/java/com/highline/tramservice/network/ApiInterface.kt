package com.highline.tramservice.network

import com.highline.tramservice.model.DeviceToken
import com.highline.tramservice.model.RoutesInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("TramTracker/RestService/GetDeviceToken/")
    fun getDeviceToken(
        @Query("aid") aid: String,
        @Query("devInfo") devInfo: String
    ): Call<DeviceToken>

    @GET("TramTracker/RestService/GetNextPredictedRoutesCollection/{stopId}/78/false/")
    fun getTramRoutesInfo(
        @Path("stopId") stopId: Int,
        @Query("aid") aid: String,
        @Query("cid") cid: Int,
        @Query("tkn") tkn: String
    ): Call<RoutesInfo>

    companion object {

        val BASE_URL = "https://ws3.tramtracker.com.au/"

        // Creates Retrofit instance.
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .run {
                    baseUrl(BASE_URL)
                        .run { addConverterFactory(GsonConverterFactory.create()) }
                        .run { build() }
                }
            return retrofit.create(ApiInterface::class.java)
        }
    }
}