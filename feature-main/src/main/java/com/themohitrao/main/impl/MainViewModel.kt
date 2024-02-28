package com.themohitrao.main.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.core_utility.mutable_types.SingleLiveEvent
import com.themohitrao.main_common.use_case.FetchJokesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchJokesUseCase: FetchJokesUseCase
) : ViewModel() {

    val userList = MutableLiveData<List<JokeDataModel>>()
    val errorValue = SingleLiveEvent<Any?>()
    val nextJokeInSeconds = SingleLiveEvent<Int>()

    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userList.postValue(fetchJokesUseCase.fetchAllJokes())
        }
    }

    fun nextJokeIn(timeInSeconds: Int) {
        nextJokeInSeconds.postValue(timeInSeconds)
    }

}