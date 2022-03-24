package com.example.chucknorris.viewModel

import com.example.chucknorris.model.JokeResult
import com.example.chucknorris.model.SingleJoke


sealed class ResultState {
    object LOADING : ResultState()
    class SUCCESS(val results: SingleJoke) : ResultState()
    class SUCCESSMULTI(val results:JokeResult) :ResultState()
    class ERROR(val error: Throwable) : ResultState()
}