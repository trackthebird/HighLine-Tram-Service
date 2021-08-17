package com.highline.tramservice.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DeviceTokenTest {

    private lateinit var responseObject : ResponseObject
    private lateinit var deviceToken: DeviceToken

    @Before
    fun setUp() {
        responseObject = ResponseObject(
            "0bb8bda1-26f5-4472-8512-65acbf2a9494",
            "AddDeviceTokenInfo" )
        deviceToken = DeviceToken(
            "errorMessage",
        false,
        true,
            arrayListOf(responseObject),
        "/Date(1628943873796+1000)/",
        "/Date(1628943873796+1000)/",
        "GetDeviceToken"
        )
    }

    @Test
    fun getErrorMessage() {
        assertTrue(deviceToken.errorMessage == "errorMessage")
    }

    @Test
    fun getHasError() {
        assertTrue(deviceToken.hasError == false)
    }

    @Test
    fun getHasResponse() {
        assertTrue(deviceToken.hasResponse == true)
    }

    @Test
    fun getResponseObject() {
        assertTrue(deviceToken.responseObject[0].DeviceToken == responseObject.DeviceToken)
    }

    @Test
    fun getTimeRequested() {
        assertTrue(deviceToken.timeRequested == "/Date(1628943873796+1000)/")
    }

    @Test
    fun getTimeResponded() {
        assertTrue(deviceToken.timeResponded == "/Date(1628943873796+1000)/")
    }

    @Test
    fun getWebMethodCalled() {
        assertTrue(deviceToken.webMethodCalled == "GetDeviceToken")
    }
}