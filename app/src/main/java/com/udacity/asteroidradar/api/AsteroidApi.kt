package com.udacity.asteroidradar.api

import android.graphics.DashPathEffect
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidApi {
    @GET("neo/rest/v1/feed")
    fun getAsteroid(
        @Query("api_key") api_key: String
    ): Deferred<String>
}


object AsteroidApiMaker {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService = retrofit.create(AsteroidApi::class.java)
}


