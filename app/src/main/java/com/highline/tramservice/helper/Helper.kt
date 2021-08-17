package com.highline.tramservice.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    /**
     * Function Checks Internet connectivity
     * Return value 'true' in case of connectivity is available and 'false' otherwise.
     */
    fun Context.isNetworkAvailable(): Boolean {
        val connection = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connection.getNetworkCapabilities(connection.activeNetwork)?.run {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else {
            connection.activeNetworkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE -> return true
                    else -> return false
                }
            }
        }
        return false
    }

    /**
     * Converts Date to specified (formatted) String date
     */
    fun Date.dateToString(): String {
        val dateFormatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        return dateFormatter.format(this)
    }

    /**
     * Function converts DotNet format date to Date
     */
    fun dateFormatDotNetDate(dotNetDate: String): String {
        with(dotNetDate) {
            val startIndex = indexOf("(") + 1
            val endIndex = indexOf("+")
            val strDate = substring(startIndex, endIndex)
            with(strDate.toLong()) {
                return Date(this).dateToString()
            }
        }
    }
}