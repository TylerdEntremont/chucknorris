package com.example.chucknorris.rest

import com.example.chucknorris.model.JokeResult
import com.example.chucknorris.model.SingleJoke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CNAPI {

    @GET(JOKE_PATH)
    suspend fun getRandomJoke(
        @Query ("exclude") exclude:String
    ): Response<SingleJoke>

    @GET(JOKE_PATH)
    suspend fun getCensoredJoke(

    )

    @GET(JOKE_PATH+"20/")
    suspend fun getTwentyJokes(
        @Query ("exclude") exclude:String
    ): Response<JokeResult>

    @GET(JOKE_PATH)
    suspend fun getNewNameJoke(
        @Query ("firstName") firstName:String,
        @Query ("lastName") lastName:String,
        @Query ("exclude") exclude:String
    ): Response<SingleJoke>


    companion object{
        const val BASE_URL = "http://api.icndb.com/"
        private const val JOKE_PATH = "jokes/random/"
    }
}