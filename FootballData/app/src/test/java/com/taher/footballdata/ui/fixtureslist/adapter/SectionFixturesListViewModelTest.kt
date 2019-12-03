package com.taher.footballdata.ui.fixtureslist.adapter

import com.taher.footballdata.R
import org.junit.Test
import org.junit.Assert.*
import java.util.*
import org.junit.Before
import org.mockito.MockitoAnnotations
import android.content.Context
import org.mockito.Mockito.*


class SectionFixturesListViewModelTest {

    private val todayString = "Today"
    private val tomorrowString = "Tomorrow"

    private val mMockContext = mock(Context::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        doReturn(todayString)
            .`when`(mMockContext)
            .getString(R.string.today)

        doReturn(tomorrowString)
            .`when`(mMockContext)
            .getString(R.string.tomorrow)
    }

    @Test
    fun constructorTest1() {
        val todayDate = Calendar.getInstance().time
        val viewModel = SectionFixturesListViewModel(mMockContext, todayDate)

        assertEquals(viewModel.day, todayString)
    }

    @Test
    fun constructorTest2() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrowDate = calendar.time

        val viewModel = SectionFixturesListViewModel(mMockContext, tomorrowDate)

        assertEquals(viewModel.day, tomorrowString)
    }

    @Test
    fun constructorTest3() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -10)
        val tomorrowDate = calendar.time

        val viewModel = SectionFixturesListViewModel(mMockContext, tomorrowDate)

        assertNotEquals(viewModel.day, tomorrowString)
        assertNotEquals(viewModel.day, todayString)
    }

    @Test
    fun constructorTest4() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 10)
        val tomorrowDate = calendar.time

        val viewModel = SectionFixturesListViewModel(mMockContext, tomorrowDate)

        assertNotEquals(viewModel.day, tomorrowString)
        assertNotEquals(viewModel.day, todayString)
    }

}