package com.example.carlocationfilteringapp.car_location.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carlocationfilteringapp.car_location.viewmodel.LocationViewModel
import com.example.carlocationfilteringapp.data.model.LocationData
import java.util.Date

@Composable
fun LocationScreen(viewModel: LocationViewModel) {
    val historyLocation by viewModel.locationHistory.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Current Location", "Historical Data")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> CurrentLocationList(currentLocation)
            1 -> HistoricalLocationList(historyLocation)
        }
    }
}


@Composable
fun CurrentLocationList(current: List<LocationData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(current) { loc ->
            LocationCard(
                title = "Current Location",
                latitude = loc.latitude,
                longitude = loc.longitude,
                timestamp = loc.timestamp,
                extra = "Car Model: ${loc.carModel}"
            )
        }
    }
}


@Composable
fun HistoricalLocationList(history: List<LocationData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(history) { loc ->
            LocationCard(
                title = "Historical Location",
                latitude = loc.latitude,
                longitude = loc.longitude,
                timestamp = loc.timestamp,
                extra = "Car Model: ${loc.carModel}"
            )
        }
    }
}


@Composable
fun LocationCard(
    title: String,
    latitude: Double,
    longitude: Double,
    timestamp: Long,
    extra: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Latitude: $latitude", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Longitude: $longitude", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Timestamp: ${Date(timestamp)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = extra,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
