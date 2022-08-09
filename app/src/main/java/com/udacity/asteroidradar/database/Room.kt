package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {
    @Query("select * from asteroiddatabase")
    fun getAsteroid(): LiveData<List<AsteroidDatabase>>

    @Query("select * from asteroiddatabase WHERE closeApproachDate = :key")
    fun getAsteroidToday(key: String): LiveData<List<AsteroidDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidDatabase)
}

@Database(entities = [AsteroidDatabase::class], version = 1)
abstract class DatabaseAsteroid : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: DatabaseAsteroid

fun getDatabase(context: Context): DatabaseAsteroid {
    synchronized(DatabaseAsteroid::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DatabaseAsteroid::class.java,
                "asteroids"
            ).build()
        }
    }
    return INSTANCE
}
