package com.taher.footballdata

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.taher.footballdata.data.datarepository.source.local.database.AppDatabase
import com.taher.footballdata.data.datarepository.source.local.database.FootballDataDao
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.data.model.Score
import com.taher.footballdata.data.model.Team
import com.taher.footballdata.data.model.Time
import com.taher.footballdata.utilities.observeOnce
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class FootballDataDaoTest {

    private lateinit var footballDataDao: FootballDataDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        footballDataDao = db.footballDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertFavoriteMatchTest() {

        val match = Match(
            id = 1,
            awayTeam = Team(1,"team1"),
            homeTeam = Team(2, "team2"),
            score = Score(
                duration = "",
                extraTime = Time(null, null),
                fullTime = Time(1, 1),
                halfTime = Time(1, 1),
                penalties = Time(null, null),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        footballDataDao.insertFavoriteMatch(match)

        footballDataDao.getAllFavoriteMatches().observeOnce {
            assertEquals(it.count(), 1)
        }
    }

    @Test
    fun getAllFavoriteMatches() {
        val match1 = Match(
            id = 1,
            awayTeam = Team(1,"team1"),
            homeTeam = Team(2, "team2"),
            score = Score(
                duration = "",
                extraTime = Time(null, null),
                fullTime = Time(1, 1),
                halfTime = Time(1, 1),
                penalties = Time(null, null),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        val match2 = Match(
            id = 2,
            awayTeam = Team(1,"team1"),
            homeTeam = Team(2, "team2"),
            score = Score(
                duration = "",
                extraTime = Time(null, null),
                fullTime = Time(1, 1),
                halfTime = Time(1, 1),
                penalties = Time(null, null),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        footballDataDao.insertFavoriteMatch(match1)
        footballDataDao.insertFavoriteMatch(match2)

        footballDataDao.getAllFavoriteMatches().observeOnce {
            assertEquals(it.count(), 2)
        }
    }


    @Test
    fun deleteFavoriteMatch() {
        val match1 = Match(
            id = 1,
            awayTeam = Team(1,"team1"),
            homeTeam = Team(2, "team2"),
            score = Score(
                duration = "",
                extraTime = Time(null, null),
                fullTime = Time(1, 1),
                halfTime = Time(1, 1),
                penalties = Time(null, null),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        val match2 = Match(
            id = 2,
            awayTeam = Team(1,"team1"),
            homeTeam = Team(2, "team2"),
            score = Score(
                duration = "",
                extraTime = Time(null, null),
                fullTime = Time(1, 1),
                halfTime = Time(1, 1),
                penalties = Time(null, null),
                winner = null
            ),
            status = "" ,
            utcDate = Calendar.getInstance().time
        )

        footballDataDao.insertFavoriteMatch(match1)
        footballDataDao.insertFavoriteMatch(match2)

        footballDataDao.deleteFavoriteMatch(match1)

        footballDataDao.getAllFavoriteMatches().observeOnce {
            assertEquals(it.count(), 1)
            assertEquals(it[0].id, 2)
        }
    }

}