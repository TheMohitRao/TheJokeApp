package com.themohitrao.main_common.use_case

interface FetchOneJokeUseCase {

    suspend fun fetchOneJoke(): Boolean
}