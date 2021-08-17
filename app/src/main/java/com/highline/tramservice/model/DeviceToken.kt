package com.highline.tramservice.model

data class DeviceToken(
    val errorMessage: Any,
    val hasError: Boolean,
    val hasResponse: Boolean,
    val responseObject: ArrayList<ResponseObject>,
    val timeRequested: String,
    val timeResponded: String,
    val webMethodCalled: String
)