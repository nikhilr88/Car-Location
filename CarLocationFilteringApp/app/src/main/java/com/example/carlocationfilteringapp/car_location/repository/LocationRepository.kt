package com.example.carlocationfilteringapp.car_location.repository

import com.example.carlocationfilteringapp.data.model.LocationData
import com.example.carlocationfilteringapp.data.repository.LocationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Repository class that abstracts data operations for location data.
 * Uses DAO to interact with the Room database.
 */
class LocationRepository @Inject constructor(
    private val dao: LocationDao
) {

    /**
     * Saves a location entry to the database.
     *
     * @param location The location data to save.
     */
    suspend fun saveLocation(location: LocationData) = dao.insert(location)

    /**
     * Retrieves location history as a Flow.
     *
     * @return A Flow emitting the list of location data.
     */
    fun getHistory(): Flow<List<LocationData>> = dao.getAll()
}
