package com.themohitrao.main_common.use_case

import com.themohitrao.core_models.JokeDataModel

interface FetchJokesUseCase {
    suspend fun fetchAllJokes(): List<JokeDataModel>

}