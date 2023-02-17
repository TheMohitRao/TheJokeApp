package com.themohitrao.main.di

import com.themohitrao.main.impl.JokeListAdapter
import com.themohitrao.main.impl.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
    factory { JokeListAdapter() }
}