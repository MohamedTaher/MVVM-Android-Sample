package com.taher.footballdata.ui.fixtureslist.adapter

import android.content.Context
import com.taher.footballdata.R
import com.taher.footballdata.utilities.extension.toFormattedString
import java.util.*

class SectionFixturesListViewModel(mContext: Context, date: Date) {

    private val dateFormat = "yyyy-MM-dd"

    var day: String

    init {
        val calendar = Calendar.getInstance()
        val todayDay = calendar.get(Calendar.DAY_OF_YEAR)

        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrowDay = calendar.get(Calendar.DAY_OF_YEAR)

        calendar.time = date
        val sectionDay = calendar.get(Calendar.DAY_OF_YEAR)

        this.day = when (sectionDay) {
            todayDay -> mContext.getString(R.string.today)
            tomorrowDay-> mContext.getString(R.string.tomorrow)
            else -> date.toFormattedString(dateFormat)
        }
    }
}