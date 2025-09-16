package com.example.carlocationfilteringapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity representing a single location data point.
 * This is used to persist location history in the local database.
 */
@Entity(tableName = "location_data")
data class LocationData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val carModel: String,
    val algorithmId: String?
)
