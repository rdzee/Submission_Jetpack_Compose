package com.androidbasic.myapplication.di

import com.androidbasic.myapplication.data.GameRepository

object Injection {
    fun provideRepository(): GameRepository {
        return GameRepository.getInstance()
    }
}