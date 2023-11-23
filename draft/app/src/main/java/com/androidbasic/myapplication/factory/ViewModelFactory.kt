package com.androidbasic.myapplication.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidbasic.myapplication.data.GameRepository
import com.androidbasic.myapplication.ui.main.JetGameViewModel

class ViewModelFactory(private val repository: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetGameViewModel::class.java)) {
            return JetGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}