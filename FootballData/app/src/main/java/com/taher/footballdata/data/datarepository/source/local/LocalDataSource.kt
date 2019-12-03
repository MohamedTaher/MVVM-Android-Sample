package com.taher.footballdata.data.datarepository.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.taher.footballdata.data.datarepository.source.DataSource
import com.taher.footballdata.data.datarepository.source.local.database.AppDatabase
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.utilities.DataWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LocalDataSource(override val kodein: Kodein): DataSource, KodeinAware {

    val database: AppDatabase by instance()

    override fun getFixturesList(): LiveData<DataWrapper<List<Match>>> {
        throw IllegalStateException("getFixturesList available only at remote data source.")
    }

    override fun getFavoriteFixturesList(): LiveData<DataWrapper<List<Match>>> {

        val matchesData = database.footballDataDao().getAllFavoriteMatches()

        return Transformations.switchMap(matchesData) {
            val sortedListByDate = it.sortedByDescending { match ->  match.utcDate.time }
            sortedListByDate.forEach { match -> match.isFavorite = true }
            val data = DataWrapper.success(sortedListByDate)
            val liveData = MutableLiveData<DataWrapper<List<Match>>>()
            liveData.value = data
            liveData
        }
    }

    override fun toggleFixtureFavoriteState(match: Match, isFavorite: Boolean) {
        GlobalScope.launch {
            if (isFavorite) {
                database.footballDataDao().insertFavoriteMatch(match)
            } else {
                database.footballDataDao().deleteFavoriteMatch(match)
            }
        }
    }
}