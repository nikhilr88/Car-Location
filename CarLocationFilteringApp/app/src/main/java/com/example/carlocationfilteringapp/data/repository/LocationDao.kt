package com.example.carlocationfilteringapp.data.repository

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.carlocationfilteringapp.data.model.LocationData
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for location data.
 * Defines database operations for the LocationData entity.
 */
@Dao
interface LocationDao {

    /**
     * Inserts a location entry into the database.
     * Replaces existing entry if there's a conflict on primary key.
     *
     * @param location The location data to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationData)

    /**
     * Retrieves all location entries ordered by timestamp (latest first).
     *
     * @return A Flow emitting the list of location data.
     */
    @Query("SELECT * FROM location_data ORDER BY timestamp DESC")
    fun getAll(): Flow<List<LocationData>>
}

/**
 * Room database class for the app.
 * Registers all entities and exposes DAOs.
 */
@Database(entities = [LocationData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides the DAO for location data.
     */
    abstract fun locationDao(): LocationDao
}
