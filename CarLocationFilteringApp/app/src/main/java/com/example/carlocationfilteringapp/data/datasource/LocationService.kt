package com.example.carlocationfilteringapp.data.datasource

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * LocationService handles location-related operations.
 * For demo purposes, it currently uses mock location updates instead of real device location.
 * The getLastLocation() or startLocationUpdates function is reserved for future use on real devices.
 *
 * @param context Application context injected via Hilt.
 */
class LocationService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // FusedLocationProviderClient is used to access location services
    private val client = LocationServices.getFusedLocationProviderClient(context)

    // LocationCallback will receive location updates
    private var locationCallback: LocationCallback? = null

    // LocationRequest defines how frequently and accurately location updates should be received
    private var locationRequest: LocationRequest? = null

    /*
     * startLocationUpdates function is reserved for future use on real devices.
     * Start receiving live location updates
     */
    @SuppressLint("MissingPermission") // Suppress warning since permission is checked manually
    fun startLocationUpdates(onLocationUpdate: (Location) -> Unit) {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Create the location request
            createLocationRequest()

            // Define the callback to handle location updates
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // Iterate through received locations
                    locationResult.locations.forEach { location ->
                        onLocationUpdate(location) // Pass location to caller
                    }
                }
            }

            // Request location updates from the fused location provider
            client.requestLocationUpdates(
                locationRequest!!,
                locationCallback!!,
                context.mainLooper // Ensure callback runs on main thread
            )
        } else {
            // Permission not granted, log warning
            Log.w("LocationService", "Location permission not granted")
        }
    }

    // Create a location request with desired parameters
    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, // High accuracy using GPS
            5000L // Desired interval for active location updates (5 seconds)
        ).apply {
            setMinUpdateIntervalMillis(2000L) // Minimum interval (2 seconds)
            setMaxUpdateDelayMillis(10000L)   // Maximum delay before delivering updates (10 seconds)
        }.build()
    }


    // Stop receiving location updates
    fun stopLocationUpdates() {
        locationCallback?.let {
            client.removeLocationUpdates(it) // Remove updates
        }
        locationCallback = null // Clear callback reference
    }


    /*
     * getLastLocation function is reserved for future use to get last known location on real devices.
     * Start receiving live location updates
     * Optional: Get last known location
     */
    @SuppressLint("MissingPermission") // Suppress warning only if permission is checked manually
    fun getLastLocation(onResult: (Location?) -> Unit) {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            client.lastLocation.addOnSuccessListener { location ->
                onResult(location)
            }
        } else {
            // Permission not granted, return null or handle appropriately
            onResult(null)
        }
    }

    // Flag to control mock location updates
    private var isMocking = false

    // Predefined list of mock locations
    private val mockLocations = listOf(
        Location(LocationManager.GPS_PROVIDER).apply {
            latitude = 18.5204
            longitude = 73.8567
        },
        Location(LocationManager.GPS_PROVIDER).apply {
            latitude = 18.5210
            longitude = 73.8575
        },
        Location(LocationManager.GPS_PROVIDER).apply {
            latitude = 18.5220
            longitude = 73.8585
        }
    )


    /**
     * Starts emitting mock location updates at a fixed interval.
     *
     * @param intervalMillis Time interval between updates in milliseconds.
     * @param onResult Callback to receive mock location updates.
     */
    fun startMockLocationUpdates(intervalMillis: Long = 3000L, onResult: (Location?) -> Unit) {
        isMocking = true
        CoroutineScope(Dispatchers.Default).launch {
            var index = 0
            while (isMocking) {
                val mockLocation = mockLocations[index % mockLocations.size].apply {
                    time = System.currentTimeMillis()
                }
                withContext(Dispatchers.Main) {
                    onResult(mockLocation)
                }
                index++
                delay(intervalMillis)
            }
        }
    }

    /**
     * Stops mock location updates.
     */
    fun stopMockLocationUpdates() {
        isMocking = false
    }

}

