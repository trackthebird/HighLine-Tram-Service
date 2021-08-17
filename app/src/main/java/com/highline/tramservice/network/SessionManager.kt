package com.highline.tramservice.network

import android.content.Context
import android.content.SharedPreferences
import com.highline.tramservice.R
import java.util.*

class SessionManager(context: Context) {
    // Define Shared preference Handler
    private var mSharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private var DEVICE_TOKEN_EXPIRY_DURATION = 3 * 60 * 1000 // 3 Minutes

    // Global Object
    companion object {
        const val DEVICE_TOKEN = "DEVICE_TOKEN"
        const val RECEIVED_TIME_IN_MILLI = "RECEIVED_TIME_IN_MILLI"
    }

    /**
     * Function saves device Token under shared preference
     */
    fun saveDeviceToken(deviceToken: String) {
        with(mSharedPreferences.edit()) {
            putString(DEVICE_TOKEN, deviceToken)
            putLong(RECEIVED_TIME_IN_MILLI, Date().time)
            apply()
        }
    }

    /**
     * Function fetches Dvice Token from shared preference
     */
    fun fetchDeviceToken(): String? {
        return with(mSharedPreferences) {
            if ((Date().time - getLong(RECEIVED_TIME_IN_MILLI, 0)) > DEVICE_TOKEN_EXPIRY_DURATION) {
                null
            } else {
                getString(DEVICE_TOKEN, null)
            }
        }
    }
}