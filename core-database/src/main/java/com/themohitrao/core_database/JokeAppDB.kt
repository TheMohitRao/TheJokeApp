package com.themohitrao.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.themohitrao.core_database.dao.JokeDao
import com.themohitrao.core_models.JokeDataModel

@Database(entities = [JokeDataModel::class], version = 1, exportSchema = true)
abstract class JokeAppDB : RoomDatabase() {
    abstract fun getJokeDao(): JokeDao
}