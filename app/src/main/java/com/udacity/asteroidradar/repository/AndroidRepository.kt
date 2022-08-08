package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.NetworkAsteroidContainer
import com.udacity.asteroidradar.api.AsteroidApiMaker
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AsteroidRepsitory(private val database: DatabaseAsteroid) {
    var getAsteroidsDatabase: LiveData<List<AsteroidDatabase>> = database.asteroidDao.getAsteroid()


    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val asteroidRequest = AsteroidApiMaker.retrofitService.getAsteroid(
                Constants.API_KEY
            ).await()

            val listAsteroid = parseAsteroidsJsonResult(asteroidRequest)
            val listConvertor=NetworkAsteroidContainer(listAsteroid)
            database.asteroidDao.insertAll(*listConvertor.asDatabaseModel())
        }
    }
}
