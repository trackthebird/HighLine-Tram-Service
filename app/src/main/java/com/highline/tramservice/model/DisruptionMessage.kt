package com.highline.tramservice.model

data class DisruptionMessage(
    val DisplayType: String,
    val MessageCount: Int,
    val Messages: ArrayList<Any>
)