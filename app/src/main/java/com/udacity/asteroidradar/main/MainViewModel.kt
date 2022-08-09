package com.udacity.asteroidradar.main

import android.app.Application

import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.checkForInternet
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepsitory
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment
    private val database = getDatabase(application)
    private val AsteroidRepository = AsteroidRepsitory(database)
    private val _picture = MutableLiveData<PictureOfDay>()
    val picture
        get() = _picture
    val asteroidsList = AsteroidRepository.getAsteroidsDatabase
    val asteroidsListToday = AsteroidRepository.getAsteroidsTodayDatabase


    init {
        viewModelScope.launch {
           if( checkForInternet(application.applicationContext)) {
               AsteroidRepository.refreshAsteroids(getToday())
               _picture.value = AsteroidRepository.refreshPicture()
           }
        }
    }


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