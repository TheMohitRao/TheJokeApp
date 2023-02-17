package com.themohitrao.core_network.api

import retrofit2.http.GET

interface JokeApiService {

    @GET(value = "api")
    suspend fun getNewJoke(): String
}