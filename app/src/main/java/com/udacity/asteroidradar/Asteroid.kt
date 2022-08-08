package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroid: ArrayList<Asteroid>)

@JsonClass(generateAdapter = true)
data class AsteroidToDatabaseContainer(val asteroid: AsteroidDatabase)


fun NetworkAsteroidContainer.asDatabaseModel(): Array<AsteroidDatabase> {
    return asteroid.map {
        AsteroidDatabase(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun AsteroidToDatabaseContainer.asDatabaseModel(): Asteroid {
    return Asteroid(
            id = asteroid.id,
            codename = asteroid.codename,
            closeApproachDate = asteroid.closeApproachDate,
            absoluteMagnitude = asteroid.absoluteMagnitude,
            estimatedDiameter = asteroid.estimatedDiameter,
            relativeVelocity = asteroid.relativeVelocity,
            distanceFromEarth = asteroid.distanceFromEarth,
            isPotentiallyHazardous = asteroid.isPotentiallyHazardous
        )
    }
