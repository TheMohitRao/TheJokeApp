package com.themohitrao.main_common.use_case

import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.main_common.repository.JokeRepository

internal class FetchJokesUseCaseImpl(private val repository: JokeRepository) :
    FetchJokesUseCase {

    override suspend fun fetchAllJokes(): List<JokeDataModel> {
        return repository.getAllJokes()
    }
}