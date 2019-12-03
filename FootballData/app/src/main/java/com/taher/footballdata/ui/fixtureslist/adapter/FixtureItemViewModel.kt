package com.taher.footballdata.ui.fixtureslist.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.taher.footballdata.data.datarepository.DataRepository
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.utilities.extension.toFormattedString

class FixtureItemViewModel(private val dataRepository: DataRepository, private val match: Match): BaseObservable() {

    private val dateFormat = "hh:mm a"

    private var isFavoriteValue: Boolean

    var homeTeamName: String
    var awayTeamName: String

    var isFinished: Boolean
    var timeString: String? = null

    var durationStateName: String
    var homeTeamScore: String
    var awayTeamScore: String

    init {
        homeTeamName = match.homeTeam.name
        awayTeamName = match.awayTeam.name

        isFinished = (match.status == Match.Status.FINISHED)
        if (isFinished.not()) {
            timeString = match.utcDate.toFormattedString(dateFormat)
        }

        val fullTime = match.score.fullTime
        durationStateName = FULL_TIME_STRING
        homeTeamScore = "${fullTime.homeTeam}"
        awayTeamScore = "${fullTime.awayTeam}"

        if (match.score.duration != Match.Duration.REGULAR) {
            val penalties = match.score.penalties
            durationStateName += " $PENALTIES_STRING"
            penalties.homeTeam?.let {homeTeamScore += "($it)" }
            penalties.awayTeam?.let {awayTeamScore += "($it)" }
        }

        isFavoriteValue = match.isFavorite
    }

    @Bindable
    fun getIsFavorite(): Boolean {
        return isFavoriteValue
    }

    fun setIsFavorite(value: Boolean) {
        if (isFavoriteValue != value) {
            isFavoriteValue = value

            dataRepository.toggleFixtureFavoriteState(match, value)
        }
    }

    companion object {
        const val FULL_TIME_STRING = "FT"
        const val PENALTIES_STRING = "(P)"
    }

}