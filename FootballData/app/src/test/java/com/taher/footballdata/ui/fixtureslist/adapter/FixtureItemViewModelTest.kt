package com.taher.footballdata.ui.fixtureslist.adapter

import com.taher.footballdata.data.datarepository.DataRepository
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.data.model.Score
import com.taher.footballdata.data.model.Team
import com.taher.footballdata.data.model.Time
import org.kodein.di.Kodein

import org.junit.Test
import org.junit.Assert.*
import java.util.*

class FixtureItemViewModelTest {
    private val testKodein = Kodein { }
    private val dataRepository = DataRepository(testKodein)

    @Test
    fun constructorTest1() {

        val match = Match(
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
            utcDate = Calendar.getInstance().time
        )

        val viewModel = FixtureItemViewModel(dataRepository, match)

        assertEquals(viewModel.homeTeamScore, "null")
        assertEquals(viewModel.awayTeamScore, "null")
    }

    @Test
    fun constructorTest2() {

        val match = Match(
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
            status = Match.Status.SCHEDULED ,
            utcDate = Calendar.getInstance().time
        )

        val viewModel = FixtureItemViewModel(dataRepository, match)

        assertNotNull(viewModel.timeString)
    }

    @Test
    fun constructorTest3() {

        val match = Match(
            id = 1,
            awayTeam = Team(1,""),
            homeTeam = Team(2, ""),
            score = Score(
                duration = Match.Duration.REGULAR,
                extraTime = Time(null, null),
                fullTime = Time(4, 2),
                halfTime = Time(null, null),
                penalties = Time(null, null),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        val viewModel = FixtureItemViewModel(dataRepository, match)

        assertEquals(viewModel.durationStateName, FixtureItemViewModel.FULL_TIME_STRING)
        assertEquals(viewModel.homeTeamScore, "2")
        assertEquals(viewModel.awayTeamScore, "4")
    }

    @Test
    fun constructorTest4() {

        val match = Match(
            id = 1,
            awayTeam = Team(1,""),
            homeTeam = Team(2, ""),
            score = Score(
                duration = Match.Duration.PENALTIES,
                extraTime = Time(null, null),
                fullTime = Time(2, 2),
                halfTime = Time(null, null),
                penalties = Time(4, 1),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        val viewModel = FixtureItemViewModel(dataRepository, match)

        assertEquals(viewModel.durationStateName, "FT (P)")
        assertEquals(viewModel.homeTeamScore, "2(1)")
        assertEquals(viewModel.awayTeamScore, "2(4)")
    }
}