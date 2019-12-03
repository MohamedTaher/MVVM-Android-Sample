package com.taher.footballdata.ui.fixtureslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.taher.footballdata.data.datarepository.DataRepository
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.utilities.DataWrapper

class FixturesListViewModel(
    private val dataRepository: DataRepository
): ViewModel() {

    val filterNumber = MutableLiveData<Int>()
    val fixturesList: LiveData<DataWrapper<List<Match>>> = Transformations.switchMap(filterNumber) {
        when(it) {
            REMOTE_FIXTURES -> dataRepository.getFixturesList()
            FAVORITE_FIXTURES -> dataRepository.getFavoriteFixturesList()
            else -> throw IllegalArgumentException("Unknown filter type")
        }
    }

    init {
        getRemoteFixtures()
    }

    fun getRemoteFixtures() {
        filterNumber.value = REMOTE_FIXTURES
    }

    fun getFavoriteFixtures() {
        filterNumber.value = FAVORITE_FIXTURES
    }


    companion object {
        const val REMOTE_FIXTURES = 1
        const val FAVORITE_FIXTURES = 2
    }
}