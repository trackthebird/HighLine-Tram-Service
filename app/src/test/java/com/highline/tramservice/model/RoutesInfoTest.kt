package com.highline.tramservice.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class RoutesInfoTest {

    private lateinit var responseObjectX : ResponseObjectX
    private lateinit var disruptionMessage : DisruptionMessage
    private lateinit var routesInfo: RoutesInfo

    @Before
    fun setUp() {
        disruptionMessage = DisruptionMessage(
            "Text",
            10,
            arrayListOf("One","Two","three")
        )

        responseObjectX = ResponseObjectX(
            false,
            "North Richmond",
            false,
            disruptionMessage,
            false,
            false,
            "78",
            78,
            false,
            true,
            "/Date(1628474748000+1000)/",
            "78",
            "Special Message",
            0,
            286,
            "NextPredictedRoutesCollectionInfo",
        )

        routesInfo = RoutesInfo(
                "No Error Message",
        false,
        true,
            arrayListOf(responseObjectX),
        "/Date(1628473802338+1000)/",
        "/Date(1628473802384+1000)/",
        "GetNextPredictedRoutesCollection",
            4052 )
    }

    @Test
    fun getErrorMessage() {
        assertTrue(routesInfo.errorMessage == "No Error Message")
    }

    @Test
    fun getHasError() {
        assertTrue(routesInfo.hasError == false)
    }

    @Test
    fun getHasResponse() {
        assertTrue(routesInfo.hasResponse == true)
    }

    @Test
    fun getResponseObject() {
        assertTrue(routesInfo.responseObject[0].SpecialEventMessage == responseObjectX.SpecialEventMessage)
    }

    @Test
    fun getTimeRequested() {
        assertTrue(routesInfo.timeRequested == "/Date(1628473802338+1000)/")
    }

    @Test
    fun getTimeResponded() {
        assertTrue(routesInfo.timeResponded == "/Date(1628473802384+1000)/")
    }

    @Test
    fun getWebMethodCalled() {
        assertTrue(routesInfo.webMethodCalled == "GetNextPredictedRoutesCollection")
    }

    @Test
    fun getStopId() {
        assertTrue(routesInfo.stopId == 4052)
    }
}