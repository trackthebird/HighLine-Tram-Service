package com.highline.tramservice.model

data class ResponseObjectX(
    val AirConditioned: Boolean,
    val Destination: String,
    val DisplayAC: Boolean,
    val DisruptionMessage: DisruptionMessage,
    val HasDisruption: Boolean,
    val HasSpecialEvent: Boolean,
    val HeadBoardRouteNo: String,
    val InternalRouteNo: Int,
    val IsLowFloorTram: Boolean,
    val IsTTAvailable: Boolean,
    val PredictedArrivalDateTime: String,
    val RouteNo: String,
    val SpecialEventMessage: String,
    val TripID: Int,
    val VehicleNo: Int,
    val __type: String
)