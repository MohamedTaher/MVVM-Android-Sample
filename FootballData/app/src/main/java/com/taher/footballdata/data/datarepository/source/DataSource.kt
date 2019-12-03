package com.taher.footballdata.data.datarepository.source

import androidx.lifecycle.LiveData
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.utilities.DataWrapper

interface DataSource {

    fun getFixturesList(): LiveData<DataWrapper<List<Match>>>

    fun getFavoriteFixturesList(): LiveData<DataWrapper<List<Match>>>

    fun toggleFixtureFavoriteState(match: Match, isFavorite: Boolean)

}