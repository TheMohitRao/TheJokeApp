package com.themohitrao.main_common.use_case

import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.main_common.events.RefreshJokeList
import com.themohitrao.main_common.repository.JokeRepository
import org.greenrobot.eventbus.EventBus

class FetchOneJokeUseCaseImpl(private val repository: JokeRepository) : FetchOneJokeUseCase {
    override suspend fun fetchOneJoke(): Boolean {
        repository.getNewJoke()?.let {
            val newData = JokeDataModel(System.currentTimeMillis(), it)
            repository.insertNewJoke(newData)
            if (repository.getAllJokes().size > 10) {
                repository.removeEarliestJoke()
            }
            EventBus.getDefault().post(RefreshJokeList())
            return true
        }
        return false
    }
}