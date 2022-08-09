package com.udacity.asteroidradar.api

import android.graphics.DashPathEffect
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface AsteroidApi {
    @GET("neo/rest/v1/feed")
    fun getAsteroid(
        @Query("api_key") api_key: String
    ,@Query("start_date") start_date: String): Deferred<String>

    @GET("planetary/apod")
    fun getpicture( @Query("api_key") api_key: String
    ): Deferred<PictureOfDay>
}


object AsteroidApiMaker {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService = retrofit.create(AsteroidApi::class.java)
}


