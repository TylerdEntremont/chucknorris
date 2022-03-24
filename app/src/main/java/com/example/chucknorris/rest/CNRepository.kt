package com.example.chucknorris.rest

import com.example.chucknorris.model.JokeResult
import com.example.chucknorris.model.SingleJoke
import retrofit2.Response

class CNRepositoryImpl(
    private val cnapi: CNAPI
) :CNRepository {

    override suspend fun getJoke(exclude: String): Response<SingleJoke> {
        return cnapi.getRandomJoke(exclude)
    }

    override suspend fun getNewNameJoke(firstName: String,lastName: String, exclude: String): Response<SingleJoke> {
        return cnapi.getNewNameJoke(firstName,lastName, exclude)
    }

    override suspend fun getTwentyJokes(exclude: String): Response<JokeResult> {
        return cnapi.getTwentyJokes(exclude)
    }
}


interface CNRepository{
    suspend fun getJoke(exclude:String):Response<SingleJoke>
    suspend fun getNewNameJoke(firstName:String,lastName:String,exclude: String):Response<SingleJoke>
    suspend fun getTwentyJokes(exclude: String):Response<JokeResult>
}