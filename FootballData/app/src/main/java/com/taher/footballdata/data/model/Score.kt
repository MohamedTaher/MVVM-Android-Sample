package com.taher.footballdata.data.model

data class Score(
    val duration: String,
    val extraTime: Time,
    val fullTime: Time,
    val halfTime: Time,
    val penalties: Time,
    val winner: String?
)