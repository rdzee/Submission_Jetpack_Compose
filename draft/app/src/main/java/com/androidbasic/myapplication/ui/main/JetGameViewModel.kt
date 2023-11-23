package com.androidbasic.myapplication.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.androidbasic.myapplication.data.GameRepository
import com.androidbasic.myapplication.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetGameViewModel(private val repository: GameRepository) : ViewModel() {
    private val _groupedGames = MutableStateFlow(
        repository.getGames()
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    )
    val groupedGames: StateFlow<Map<Char, List<Game>>> get() = _groupedGames

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedGames.value = repository.searchGames(_query.value)
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    }
}
