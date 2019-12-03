package com.taher.footballdata.data.model

data class Competition(
    val area: Area,
    val code: String,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val plan: String
)