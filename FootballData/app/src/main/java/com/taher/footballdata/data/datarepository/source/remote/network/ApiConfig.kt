package com.taher.footballdata.data.datarepository.source.remote.network

object ApiConfig {

    const val BASE_URL = "https://api.football-data.org/v2/"

    object ApiKey {
        const val NAME = "X-Auth-Token"
        const val VALUE = "fb8f8bb211b04e2f9d3d7a6276314bc2"
    }

    const val GET_ALL_FIXTURES = "competitions/2021/matches"
}