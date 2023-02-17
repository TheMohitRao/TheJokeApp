package com.themohitrao.core_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JokeDataModel(
    @PrimaryKey
    val downloadTimeStamp: Long,
    val content: String
)