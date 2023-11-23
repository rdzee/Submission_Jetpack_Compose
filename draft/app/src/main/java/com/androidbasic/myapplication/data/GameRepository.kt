package com.androidbasic.myapplication.data

import com.androidbasic.myapplication.model.Game
import com.androidbasic.myapplication.model.GamesData

class GameRepository {
    fun getGames(): List<Game> {
        return GamesData.games
    }

    fun searchGames(query: String): List<Game>{
        return GamesData.games.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(): GameRepository =
            instance ?: synchronized(this) {
                GameRepository().apply {
                    instance = this
                }
            }
    }
}