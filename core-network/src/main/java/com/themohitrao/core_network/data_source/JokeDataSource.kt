package com.themohitrao.core_network.data_source

/**
 * Interface representing network calls to the backend
 */
interface JokeDataSource {
    suspend fun getNewJoke(): String?
}
