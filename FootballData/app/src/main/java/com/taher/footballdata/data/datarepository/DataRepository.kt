package com.taher.footballdata.data.datarepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.taher.footballdata.data.datarepository.source.DataSource
import com.taher.footballdata.data.datarepository.source.local.LocalDataSource
import com.taher.footballdata.data.datarepository.source.remote.RemoteDataSource
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.utilities.DataWrapper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DataRepository(override val kodein: Kodein): DataSource, KodeinAware {

    private val remoteDataSource = RemoteDataSource(kodein)
    private val localDataSource = LocalDataSource(kodein)

    fun combineMatchesRemoteAndLocalData(remoteData: DataWrapper<List<Match>>, localData: DataWrapper<List<Match>>): DataWrapper<List<Match>>? {

        if(remoteData.status != DataWrapper.Status.SUCCESS) {
            return null
        }

        val favoriteList = localData.data ?: listOf()
        val dataWithFavoriteState = ArrayList(remoteData.data ?: listOf())
        dataWithFavoriteState.forEach { match ->
            val foundItem = favoriteList.firstOrNull { x -> x.id ==  match.id}
            match.isFavorite = (foundItem != null)
        }

        return DataWrapper.success(dataWithFavoriteState)
    }

    override fun getFixturesList(): LiveData<DataWrapper<List<Match>>> {

        val matchesRemoteData = remoteDataSource.getFixturesList()
        val matchesLocalData= localDataSource.getFavoriteFixturesList()

        val result = MediatorLiveData<DataWrapper<List<Match>>>()

        result.addSource(matchesRemoteData) {
            combineMatchesRemoteAndLocalData(
                remoteData = matchesRemoteData.value ?: DataWrapper.none(),
                localData = matchesLocalData.value ?: DataWrapper.none()
            )?.let { result.value = it }
        }

        result.addSource(matchesLocalData) {
            combineMatchesRemoteAndLocalData(
                remoteData = matchesRemoteData.value ?: DataWrapper.none(),
                localData = matchesLocalData.value ?: DataWrapper.none()
            )?.let { result.value = it }
        }

        return result
    }

    override fun getFavoriteFixturesList(): LiveData<DataWrapper<List<Match>>> {
        return localDataSource.getFavoriteFixturesList()
    }

    override fun toggleFixtureFavoriteState(match: Match, isFavorite: Boolean) {
        localDataSource.toggleFixtureFavoriteState(match, isFavorite)
    }

}