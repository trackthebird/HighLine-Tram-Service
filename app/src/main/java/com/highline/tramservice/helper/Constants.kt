package com.highline.tramservice.helper

object Constants {
    const val DEVICE_TOKEN_INFO = "AddDeviceTokenInfo"
    const val NORTH_BOUND_STOPID = 4055
    const val SOUTH_BOUND_STOPID = 4155
    val STOP_ID_LISTS: List<Int> = arrayListOf(NORTH_BOUND_STOPID, SOUTH_BOUND_STOPID)

    val DATE_FORMAT = "HH:MM aa, dd MMM yyyy"

    enum class APP_STATE(private val type: Int) {
        REQUEST_SENT(1),
        RESPONSE_SUCCESS(2),
        RESPONSE_SUCCESS01(3),
        RESPONSE_SUCCESS02(4),
        RESPONSE_FAILED(5),
        REQUEST_TO_CLEAR(6),
        REQUEST_TO_REFRESH(7),
        UN_KNOW(8),
        PROCESSING(9)
    }
}