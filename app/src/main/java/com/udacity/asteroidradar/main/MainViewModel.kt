package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepsitory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment
    private val database = getDatabase(application)
    val asteroids = database.asteroidDao.getAsteroid()
    private val videosRepository = AsteroidRepsitory(database)

    init {
        viewModelScope.launch {
            videosRepository.refreshVideos()
        }
    }

    val asteroidsList = videosRepository.getAsteroidsDatabase

    fun onDetailFragmentClick(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

    fun onDetailFragmentNavigated() {
        _navigateToDetailFragment.value = null
    }




    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}