package com.example.carlocationfilteringapp.car_location.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carlocationfilteringapp.car_location.repository.LocationRepository
import com.example.carlocationfilteringapp.data.algorithm.AlgorithmFactory
import com.example.carlocationfilteringapp.data.datasource.LocationService
import com.example.carlocationfilteringapp.data.model.CarModel
import com.example.carlocationfilteringapp.data.model.LocationData
import com.example.carlocationfilteringapp.data.model.ProcessedLocationPoint
import com.example.carlocationfilteringapp.data.model.RawLocationPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing location updates and processing.
 * It uses a car-model-specific algorithm to filter raw location data,
 * stores processed data in memory and persists it via a repository.
 *
 * Dependencies are injected via Hilt:
 * - LocationService: provides location data (mock or real)
 * - LocationRepository: handles persistence of location history
 * - AlgorithmFactory: maps car models to filtering algorithms
 */
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationService: LocationService,
    private val repository: LocationRepository,
    private val algorithmFactory: AlgorithmFactory,
) : ViewModel() {

    // Currently hardcoded car model; can be made dynamic later (e.g., from user settings or config)
    private val carModel = CarModel.MODEL_A

    // Holds the latest processed location points in memory
    private val _currentLocation = MutableStateFlow<List<LocationData>>(emptyList())
    val currentLocation: StateFlow<List<LocationData>> = _currentLocation

    // Exposes location history from repository as a StateFlow
    val locationHistory = repository.getHistory().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    /**
     * Starts location updates using the mock location service.
     * Applies the car-model-specific filtering algorithm to each location point.
     */
    fun startLocationUpdates() {
        val algorithm = algorithmFactory.forCarModel(carModel)
        viewModelScope.launch {
            locationService.startMockLocationUpdates { location ->
                location?.let { locationData ->
                    val data = RawLocationPoint(
                        latitude = locationData.latitude,
                        longitude = locationData.longitude,
                        timestamp = System.currentTimeMillis(),
                    )
                    // Apply filtering algorithm
                    val processedPoints = algorithm.process(listOf(data))
                    // Save each processed point
                    processedPoints.forEach { saveLocation(it) }
                }
            }
        }
    }


    /**
     * Saves a processed location point to memory and repository.
     */
    private fun saveLocation(location: ProcessedLocationPoint) {
        viewModelScope.launch {
            location.let { locationData ->
                val data = LocationData(
                    latitude = locationData.latitude,
                    longitude = locationData.longitude,
                    timestamp = System.currentTimeMillis(),
                    carModel = carModel.displayName,
                    algorithmId = locationData.algorithmId
                )

                // Update current location (in-memory)
                _currentLocation.value += data
                // Persist to repository
                repository.saveLocation(data)
            }
        }
    }


    /**
     * Stops mock location updates.
     * Should be called from lifecycle-aware components (e.g., onStop).
     */
    fun stopMockLocationUpdates() {
        locationService.stopMockLocationUpdates()
    }
}
