package com.taher.footballdata.data.datarepository.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taher.footballdata.data.datarepository.source.DataSource
import com.taher.footballdata.data.datarepository.source.remote.network.ApiInterface
import com.taher.footballdata.data.datarepository.source.remote.network.ErrorBody
import com.taher.footballdata.data.model.GetAllFixturesResponse
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.utilities.DataWrapper
import com.taher.footballdata.utilities.extension.getZeroTimeDate
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class RemoteDataSource(override val kodein: Kodein): DataSource, KodeinAware {

    private val footballDataApi: ApiInterface  by instance()

    fun filterMatchesList(matches: List<Match>): List<Match> {
        val result = ArrayList<Match>()

        val calendar = Calendar.getInstance()
        val todayDate = calendar.time.getZeroTimeDate()

        val pastMatches = matches.filter { it.utcDate.getZeroTimeDate() < todayDate }
        result.addAll(pastMatches)

        val todayMatches = matches.filter { it.utcDate.getZeroTimeDate() == todayDate }
        if (todayMatches.count() > 0) {
            result.addAll(todayMatches)

        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrowDate = calendar.time.getZeroTimeDate()
            val tomorrowMatches = matches.filter { it.utcDate.getZeroTimeDate() == tomorrowDate }
            result.addAll(tomorrowMatches)
        }

        result.sortByDescending { it.utcDate }
        return result
    }

    override fun getFixturesList(): LiveData<DataWrapper<List<Match>>> {
        val matchesData = MutableLiveData<DataWrapper<List<Match>>>()

        footballDataApi.getAllFixtures().enqueue(object : Callback<GetAllFixturesResponse> {

            override fun onResponse( call: Call<GetAllFixturesResponse>, response: Response<GetAllFixturesResponse> ) {
                if (response.isSuccessful) {
                    val getAllFixturesResponse = response.body()
                    val matches = getAllFixturesResponse?.matches ?: listOf()
                    val filteredMatches = filterMatchesList(matches)
                    matchesData.value = DataWrapper.success(filteredMatches)

                } else {
                    val errorBodyJsonString = response.errorBody()?.string()
                    val errorBody = errorBodyJsonString?.let { ErrorBody.instance(it) }
                    matchesData.value = DataWrapper.error(errorBody?.message)
                }
            }

            override fun onFailure(call: Call<GetAllFixturesResponse>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
                matchesData.value = DataWrapper.none()
            }
        })

        return matchesData
    }

    override fun getFavoriteFixturesList(): MutableLiveData<DataWrapper<List<Match>>> {
        throw IllegalStateException("getFavoriteFixturesList available only at data source.")
    }

    override fun toggleFixtureFavoriteState(match: Match, isFavorite: Boolean) {
        throw IllegalStateException("toggleFixtureFavoriteState available only at data source.")
    }

    companion object {
        const val TAG = "RemoteDataSource"

    }
}