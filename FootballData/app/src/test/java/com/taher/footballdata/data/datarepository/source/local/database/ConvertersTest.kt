package com.taher.footballdata.data.datarepository.source.local.database

import org.junit.Test

import org.junit.Assert.*
import java.util.*
import java.util.Calendar.*

class ConvertersTest {

    private val calender: Calendar = getInstance().apply {
        set(YEAR, 1995)
        set(MONTH, OCTOBER)
        set(DAY_OF_MONTH, 4)
    }

    @Test
    fun dateToTimestamp() {
        assertEquals(calender.timeInMillis, Converters().dateToTimestamp(calender.time))
    }

    @Test
    fun timestampToDate() {
        assertEquals(calender.time, Converters().timestampToDate(calender.timeInMillis))
    }

//    @Test
//    fun teamToString() {
//    }
//
//    @Test
//    fun stringToTeam() {
//    }
//
//    @Test
//    fun scoreToString() {
//    }
//
//    @Test
//    fun stringToScore() {
//    }
}