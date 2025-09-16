
# Car Location Filtering App

## Project Overview
This micro project is designed to present location-based data for cars. 
To ensure high-quality data presentation, the application applies filtering algorithms to raw location data. 
These algorithms are provided by a neighboring team and vary depending on the car model. 
The application also stores location history while it is running, allowing users to view past data.

## Key Features
- Real-time location tracking (mocked for demo purposes)
- Car-model-specific location filtering algorithms
- In-memory and persistent storage of location history
- Jetpack Compose UI for displaying location data
- Hilt for dependency injection
- Room database for local data persistence

## Architecture
- **MVVM (Model-View-ViewModel)** architecture is used to separate concerns.
- **Hilt** is used for dependency injection.
- **Room** is used for local database storage.
- **StateFlow** is used for reactive data updates.
- **Jetpack Compose** is used for UI rendering.

## Modules and Components

### 1. `LocationService`
Handles location updates using mock data for demo purposes. 
Real location retrieval is implemented but not used currently.

### 2. `LocationViewModel`
Manages location data, applies filtering algorithms, and interacts with the repository.

### 3. `LocationRepository`
Provides access to the Room database for saving and retrieving location history.

### 4. `LocationDao`
Defines database operations for the `LocationData` entity.

### 5. `AppDatabase`
Room database configuration that registers the `LocationDao`.

### 6. `AlgorithmFactory`
Maps car models to their respective filtering algorithms.

### 7. `LocationFilterAlgorithm`
Interface for filtering algorithms.

## Use Cases
- Start the app manually to begin location tracking.
- View real-time location updates (mocked).
- Apply car-model-specific filtering to raw location data.
- Store and retrieve location history while the app is running.

## Notes
- The app does not store data when not running.
- Filtering algorithms are external and integrated via a factory.
- Location permission is requested at runtime.

## Setup Instructions
1. Clone the repository.
2. Open in Android Studio.
3. Build and run the app on an emulator or device.

## Future Improvements
- Integrate real location updates.
- Make car model selection dynamic.
- Add permission rationale and settings fallback.

