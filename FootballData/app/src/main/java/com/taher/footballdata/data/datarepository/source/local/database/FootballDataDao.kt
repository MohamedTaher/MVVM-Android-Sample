package com.taher.footballdata.data.datarepository.source.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taher.footballdata.data.model.Match

@Dao
interface FootballDataDao {

    @Query("SELECT * FROM favorite_matches")
    fun getAllFavoriteMatches(): LiveData<List<Match>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMatch(match: Match): Long

    @Delete
    fun deleteFavoriteMatch(match: Match)
}