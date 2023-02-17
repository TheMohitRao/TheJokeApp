package com.themohitrao.core_network.data_source

import com.themohitrao.core_network.api.JokeApiService
import retrofit2.Retrofit

/**
 * [Retrofit] backed [JokeDataSource]
 */
class JokeDataSourceImpl(private val jokeApiService: JokeApiService) : JokeDataSource {

    override suspend fun getNewJoke(): String {
        return jokeApiService.getNewJoke()
    }

}
