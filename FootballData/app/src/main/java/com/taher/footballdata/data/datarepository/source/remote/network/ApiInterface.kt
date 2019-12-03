package com.taher.footballdata.data.datarepository.source.remote.network

import com.taher.footballdata.data.model.GetAllFixturesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiConfig.GET_ALL_FIXTURES)
    fun getAllFixtures(): Call<GetAllFixturesResponse>
}