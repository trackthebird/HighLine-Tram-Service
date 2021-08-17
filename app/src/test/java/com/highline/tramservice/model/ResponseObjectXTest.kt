package com.highline.tramservice.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ResponseObjectXTest {

    private lateinit var responseObjectX : ResponseObjectX
    private lateinit var disruptionMessage : DisruptionMessage

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
    }

    @Test
    fun getAirConditioned() {
        assertTrue(responseObjectX.AirConditioned == false)
    }

    @Test
    fun getDestination() {
        assertTrue(responseObjectX.Destination == "North Richmond")
    }

    @Test
    fun getDisplayAC() {
        assertTrue(responseObjectX.AirConditioned == false)
    }

    @Test
    fun getDisruptionMessage() {
        assertTrue(responseObjectX.AirConditioned == false)
    }

    @Test
    fun getHasDisruption() {
        assertTrue(responseObjectX.HasDisruption == false)
    }

    @Test
    fun getHasSpecialEvent() {
        assertTrue(responseObjectX.HasDisruption == false)
    }

    @Test
    fun getHeadBoardRouteNo() {
        assertTrue(responseObjectX.HeadBoardRouteNo == "78")
    }

    @Test
    fun getInternalRouteNo() {
        assertTrue(responseObjectX.InternalRouteNo == 78)
    }


    @Test
    fun getIsLowFloorTram() {
        assertTrue(responseObjectX.IsLowFloorTram == false)
    }

    @Test
    fun getIsTTAvailable() {
        assertTrue(responseObjectX.IsTTAvailable == true)
    }

    @Test
    fun getPredictedArrivalDateTime() {
        assertTrue(responseObjectX.PredictedArrivalDateTime == "/Date(1628474748000+1000)/")
    }

    @Test
    fun getRouteNo() {
        assertTrue(responseObjectX.RouteNo == "78")
    }

    @Test
    fun getSpecialEventMessage() {
        assertTrue(responseObjectX.SpecialEventMessage == "Special Message")
    }

    @Test
    fun getTripID() {
        assertTrue(responseObjectX.TripID == 0)
    }

    @Test
    fun getVehicleNo() {
        assertTrue(responseObjectX.VehicleNo == 286)
    }

    @Test
    fun get__type() {
        assertTrue(responseObjectX.__type == "NextPredictedRoutesCollectionInfo")
    }

}