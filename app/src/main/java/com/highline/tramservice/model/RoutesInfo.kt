package com.highline.tramservice.model

data class RoutesInfo(
    val errorMessage: Any,
    val hasError: Boolean,
    val hasResponse: Boolean,
    val responseObject: ArrayList<ResponseObjectX>,
    val timeRequested: String,
    val timeResponded: String,
    val webMethodCalled: String,
    var stopId: Int
)