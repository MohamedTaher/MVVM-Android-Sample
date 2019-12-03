package com.taher.footballdata.data.datarepository

import com.taher.footballdata.data.model.Match
import com.taher.footballdata.data.model.Score
import com.taher.footballdata.data.model.Team
import com.taher.footballdata.data.model.Time
import com.taher.footballdata.utilities.DataWrapper
import org.kodein.di.Kodein
import kotlin.collections.ArrayList

import org.junit.Test
import org.junit.Assert.*
import java.util.*

class DataRepositoryTest {

    private val testKodein = Kodein { }

    private val matchesList = listOf(
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
            status = "",
            utcDate = Calendar.getInstance().time
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
            status = "",
            utcDate = Calendar.getInstance().time
        )
    )

    //Success calling the api
    @Test
    fun combineMatchesRemoteAndLocalDataTest1() {

        val remoteMatches = ArrayList(matchesList)
        val remoteData = DataWrapper.success(remoteMatches)

        val favoriteMatches = listOf(matchesList[0])
        val localData = DataWrapper.success(favoriteMatches)

        val resultData = DataRepository(testKodein).combineMatchesRemoteAndLocalData(remoteData, localData)
        assertNotNull(resultData)

        val numberOfFavoriteItems = resultData?.data?.count{ it.isFavorite }
        assertEquals(numberOfFavoriteItems, 1)
    }

    //Failure calling the api
    @Test
    fun combineMatchesRemoteAndLocalDataTest2() {

        val remoteData = DataWrapper.error("", null)

        val favoriteMatches = ArrayList(matchesList)
        val localData = DataWrapper.success(favoriteMatches)

        val resultData = DataRepository(testKodein).combineMatchesRemoteAndLocalData(remoteData, localData)
        assertNull(resultData)
    }


}