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

    suspend fun getNewJoke(): Boolean {
        jokeDataSource.getNewJoke()?.let {
            val newData = JokeDataModel(System.currentTimeMillis(), it)
            insertNewJoke(newData)
            if (getAllJokes().size > 10) {
                removeEarliestJoke()
            }
            EventBus.getDefault().post(RefreshJokeList())
            return true
        }
        return false
    }

    suspend fun getAllJokes(): List<JokeDataModel> {
        return jokeDao.getAllJokes()
    }

    private suspend fun insertNewJoke(joke: JokeDataModel) {
        jokeDao.insertJokeData(joke)
    }

    private suspend fun removeEarliestJoke() {
        jokeDao.removeEarliestJoke()
    }

}