package com.example.carlocationfilteringapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Application class annotated for Hilt dependency injection setup.
// This is the entry point for Hilt to start providing dependencies across the app
@HiltAndroidApp
class MyApplication : Application()
