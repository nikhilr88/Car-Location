package com.example.carlocationfilteringapp.data.repository

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


/**
 * Hilt module that provides Room database and DAO instances.
 * Installed in SingletonComponent to ensure single instance across the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of the Room database.
     *
     * @param context Application context injected by Hilt.
     * @return AppDatabase instance.
     */
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "location_db").build()

    /**
     * Provides the DAO for accessing location data.
     *
     * @param db The Room database instance.
     * @return LocationDao instance.
     */
    @Provides
    fun provideDao(db: AppDatabase): LocationDao = db.locationDao()
}
