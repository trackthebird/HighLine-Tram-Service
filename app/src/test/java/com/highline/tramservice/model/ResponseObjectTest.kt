package com.highline.tramservice.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ResponseObjectTest {

    private lateinit var responseObject : ResponseObject

    @Before
    fun setUp() {
        responseObject = ResponseObject(
                "0bb8bda1-26f5-4472-8512-65acbf2a9494",
                "AddDeviceTokenInfo" )
    }

    @Test
    fun getDeviceToken() {
        assertTrue(responseObject.DeviceToken == "0bb8bda1-26f5-4472-8512-65acbf2a9494")
    }

    @Test
    fun get__type() {
        assertTrue(responseObject.__type == "AddDeviceTokenInfo")
    }
}