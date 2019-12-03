package com.taher.footballdata.data.model

data class GetAllFixturesResponse(
    val competition: Competition,
    val count: Int,
    val filters: Filters,
    val matches: List<Match>
)