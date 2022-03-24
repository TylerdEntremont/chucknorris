package com.example.chucknorris.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorris.rest.CNRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokeViewModel(
    private val cnRepository: CNRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _joke: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val joke: LiveData<ResultState> get() = _joke

    fun getJoke(exclude:String) {
        viewModelScope.launch(ioDispatcher) {
            // here is the worker thread
            try {
                val response = cnRepository.getJoke(exclude)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // here i have my forecast for the city
                        //_joke.postValue(ResultState.SUCCESS(it))
                        withContext(Dispatchers.Main) {
                             //here I am in the MAIN thread
                            _joke.value = ResultState.SUCCESS(it)
                        }
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception("No success")
                }
            } catch (e: Exception) {
                _joke.postValue(ResultState.ERROR(e))
            }
        }
    }

    fun getNewNameJoke(firstName:String, lastName:String, exclude: String) {
        viewModelScope.launch(ioDispatcher) {
            // here is the worker thread
            try {
                val response = cnRepository.getNewNameJoke(firstName,lastName, exclude)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // here i have my forecast for the city
                        // _cityForecast.postValue(ResultState.SUCCESS(it))
                        withContext(Dispatchers.Main) {
                            // here I am in the MAIN thread
                            _joke.value = ResultState.SUCCESS(it)
                        }
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception("No success")
                }
            } catch (e: Exception) {
                _joke.postValue(ResultState.ERROR(e))
            }
        }
    }

    fun getTwentyJokes(exclude: String) {
        viewModelScope.launch(ioDispatcher) {
            // here is the worker thread
            try {
                val response = cnRepository.getTwentyJokes(exclude)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // here i have my forecast for the city
                        // _cityForecast.postValue(ResultState.SUCCESS(it))
                        withContext(Dispatchers.Main) {
                            // here I am in the MAIN thread
                            _joke.value = ResultState.SUCCESSMULTI(it)
                        }
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception("No success")
                }
            } catch (e: Exception) {
                _joke.postValue(ResultState.ERROR(e))
            }
        }
    }

}