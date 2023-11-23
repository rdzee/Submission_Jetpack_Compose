package com.androidbasic.myapplication.model

data class Game(
    val rank: Int,
    val title: String,
    val description: String,
    val photoUrl: String,
    val release: String,
    val genres: String
)