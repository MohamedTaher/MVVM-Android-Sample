package com.taher.footballdata.data.datarepository.source.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.taher.footballdata.data.model.Score
import com.taher.footballdata.data.model.Team
import java.util.*

class Converters {

    private val gson = Gson()

    @TypeConverter fun dateToTimestamp(date: Date): Long {
        val calendar = Calendar.getInstance().apply { time = date }
        return calendar.timeInMillis
    }

    @TypeConverter fun timestampToDate(value: Long): Date {
        val calendar = Calendar.getInstance().apply { timeInMillis = value }
        return calendar.time
    }

    @TypeConverter fun teamToString(team: Team): String {
        return gson.toJson(team)
    }

    @TypeConverter fun stringToTeam(teamString: String): Team {
        return gson.fromJson(teamString, Team::class.java)
    }

    @TypeConverter fun scoreToString(score: Score): String {
        return gson.toJson(score)
    }


    @TypeConverter fun stringToScore(scoreString: String): Score {
        return gson.fromJson(scoreString, Score::class.java)
    }

}