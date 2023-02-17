package com.themohitrao.core_database.di

import androidx.room.Room
import com.themohitrao.core_database.JokeAppDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            JokeAppDB::class.java,
            "joke_data_database"
        ).build()
    }
    single {
        get<JokeAppDB>().getJokeDao()
    }
}
