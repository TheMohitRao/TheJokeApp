package com.themohitrao.main_common.repository

import com.themohitrao.core_database.dao.JokeDao
import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.core_network.data_source.JokeDataSource
import com.themohitrao.main_common.events.RefreshJokeList
import org.greenrobot.eventbus.EventBus

class JokeRepository(
    private val jokeDao: JokeDao,
    private val jokeDataSource: JokeDataSource
) {

    suspend fun getNewJoke(): String? {
        return jokeDataSource.getNewJoke()
    }

    suspend fun getAllJokes(): List<JokeDataModel> {
        return jokeDao.getAllJokes()
    }

    internal suspend fun insertNewJoke(joke: JokeDataModel) {
        jokeDao.insertJokeData(joke)
    }

    internal suspend fun removeEarliestJoke() {
        jokeDao.removeEarliestJoke()
    }

}