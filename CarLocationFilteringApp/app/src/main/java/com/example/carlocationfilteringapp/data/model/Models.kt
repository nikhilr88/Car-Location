package com.example.carlocationfilteringapp.data.model

/** Car models supported. Extend as you onboard more models. */
enum class CarModel(val displayName: String) {
    MODEL_A("Model A"), // uses smoothing
    MODEL_B("Model B"), // uses simplified Kalman
    MODEL_C("Model C"); // uses median filter (example)
}

/** Raw point as received from source (mock or device). */
data class RawLocationPoint(
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val speedMps: Double? = null, // optional
)

/** Processed point after algorithm. */
data class ProcessedLocationPoint(
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val speedMps: Double? = null,
    val algorithmId: String
)

