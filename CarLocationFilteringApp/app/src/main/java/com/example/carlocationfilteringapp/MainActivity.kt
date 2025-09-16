package com.example.carlocationfilteringapp

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.carlocationfilteringapp.car_location.ui.LocationScreen
import com.example.carlocationfilteringapp.car_location.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint


// Marks this activity as a Hilt injection entry point.
// Required for injecting dependencies into Android components.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ViewModel scoped to this activity, provided via Hilt.
    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request location permission when the activity is created.
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        // Set the UI content using Jetpack Compose.
        setContent {
            // Pass the ViewModel to the composable screen.
            LocationScreen(viewModel)
        }
    }


    // Launcher to handle location permission request result.
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted: start location updates via ViewModel.
            viewModel.startLocationUpdates()
        } else {
            // Permission denied, show message or fallback
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onStop() {
        super.onStop()
        // Stop mock location updates when the activity goes to background.
        viewModel.stopMockLocationUpdates()
    }
}

