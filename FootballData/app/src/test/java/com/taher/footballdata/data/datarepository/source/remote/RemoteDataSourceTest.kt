package com.taher.footballdata.data.datarepository.source.remote

import com.taher.footballdata.data.model.Match
import com.taher.footballdata.data.model.Score
import com.taher.footballdata.data.model.Team
import com.taher.footballdata.data.model.Time
import org.junit.Test
import org.junit.Assert.*
import java.util.*

class RemoteDataSourceTest {

    private val todayDate: Date
    private val tomorrowDate: Date
    private val dayBeforeDate: Date
    private val afterTomorrowDate: Date

    init {
        val calendar = Calendar.getInstance()
        todayDate = calendar.time

        calendar.add(Calendar.DAY_OF_YEAR, 1)
        tomorrowDate = calendar.time

        calendar.add(Calendar.DAY_OF_YEAR, 1)
        afterTomorrowDate = calendar.time

        calendar.add(Calendar.DAY_OF_YEAR, -3)
        dayBeforeDate = calendar.time
    }

    private fun getMatchesList(date0: Date, date1: Date, date2: Date) =
        listOf(
            Match(
                id = 1,
                awayTeam = Team(1,""),
                homeTeam = Team(2, ""),
                score = Score(
                    duration = "",
                    extraTime = Time(null, null),
                    fullTime = Time(null, null),
                    halfTime = Time(null, null),
                    penalties = Time(null, null),
                    winner = null
                ),
                status = "" ,
                utcDate = date0
            ),
            Match(
                id = 2,
                awayTeam = Team(1,""),
                homeTeam = Team(2, ""),
                score = Score(
                    duration = "",
                    extraTime = Time(null, null),
                    fullTime = Time(null, null),
                    halfTime = Time(null, null),
                    penalties = Time(null, null),
                    winner = null
                ),
                status = "" ,
                utcDate = date1
            ),
            Match(
                id = 3,
                awayTeam = Team(1,""),
                homeTeam = Team(2, ""),
                score = Score(
                    duration = "",
                    extraTime = Time(null, null),
                    fullTime = Time(null, null),
                    halfTime = Time(null, null),
                    penalties = Time(null, null),
                    winner = null
                ),
                status = "" ,
                utcDate = date2
            )
        )

    @Test
    fun filterMatchesListTest1() {
        val matchesList = getMatchesList(dayBeforeDate, todayDate, tomorrowDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 2)
    }

    @Test
    fun filterMatchesListTest2() {
        val matchesList = getMatchesList(dayBeforeDate, dayBeforeDate, todayDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 3)
    }

    @Test
    fun filterMatchesListTest3() {
        val matchesList = getMatchesList(dayBeforeDate, dayBeforeDate, tomorrowDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 3)
    }

    @Test
    fun filterMatchesListTest4() {
        val matchesList = getMatchesList(dayBeforeDate, dayBeforeDate, dayBeforeDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 3)
    }

    @Test
    fun filterMatchesListTest5() {
        val matchesList = getMatchesList(todayDate, tomorrowDate, tomorrowDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 1)
    }

    @Test
    fun filterMatchesListTest6() {
        val matchesList = getMatchesList(todayDate, todayDate, todayDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 3)
    }

    @Test
    fun filterMatchesListTest7() {
        val matchesList = getMatchesList(tomorrowDate, tomorrowDate, tomorrowDate)
        val resultList = RemoteDataSource().filterMatchesList(matchesList)

        assertEquals(resultList.count(), 3)
    }


}