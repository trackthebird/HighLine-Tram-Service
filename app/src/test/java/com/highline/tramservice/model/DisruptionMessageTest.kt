package com.highline.tramservice.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DisruptionMessageTest {

    private lateinit var disruptionMessage : DisruptionMessage


    @Before
    fun setUp() {
        disruptionMessage = DisruptionMessage(
             "Text",
         10,
                     arrayListOf("One","Two","three")
        )
    }

    @Test
    fun getDisplayType() {
        assertTrue(disruptionMessage.DisplayType == "Text")
    }

    @Test
    fun getMessageCount() {
        assertTrue(disruptionMessage.MessageCount == 10)
    }

    @Test
    fun getMessages() {
        assertTrue(disruptionMessage.Messages.size == 3)
        assertTrue(disruptionMessage.Messages[0] == "One")
        assertTrue(disruptionMessage.Messages[1] == "Two")
    }

    @Test
    fun testInvalidGetDisplayType() {
        assertFalse(disruptionMessage.DisplayType == "Text1")
    }

    @Test
    fun testInvalidGetMessageCount() {
        assertFalse(disruptionMessage.MessageCount == 100)
    }

    @Test
    fun testInvalidGetMessages() {
        assertFalse(disruptionMessage.Messages.size == 0)
        assertFalse(disruptionMessage.Messages[0] == "Two")
        assertFalse(disruptionMessage.Messages[1] == "One")
    }
}