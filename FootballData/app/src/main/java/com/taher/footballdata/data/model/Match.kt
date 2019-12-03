package com.taher.footballdata.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favorite_matches")
data class Match(
    @PrimaryKey val id: Int,
    val awayTeam: Team,
    val homeTeam: Team,
    val score: Score,
    val status: String,
    val utcDate: Date
) {

    @Ignore var isFavorite = false

    object Status {
        const val FINISHED = "FINISHED"
        const val SCHEDULED = "SCHEDULED"
    }

    object Duration {
        const val REGULAR = "REGULAR"
        const val PENALTIES = "PENALTIES"
    }


}