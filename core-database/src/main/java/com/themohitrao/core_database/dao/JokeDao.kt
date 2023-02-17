package com.themohitrao.core_database.dao

import androidx.room.*
import com.themohitrao.core_models.JokeDataModel

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokeData(jokeData: JokeDataModel)

    @Query("SELECT * from jokeDataModel ORDER BY downloadTimeStamp DESC")
    suspend fun getAllJokes(): List<JokeDataModel>

    @Query("DELETE from jokeDataModel")
    fun removeAllJokes()

    @Query("DELETE FROM jokeDataModel WHERE downloadTimeStamp IN (SELECT downloadTimeStamp FROM jokeDataModel ORDER BY downloadTimeStamp ASC LIMIT 1)")
    suspend fun removeEarliestJoke()
}