package com.example.carlocationfilteringapp.data.algorithm

import com.example.carlocationfilteringapp.data.model.ProcessedLocationPoint
import com.example.carlocationfilteringapp.data.model.RawLocationPoint


/**
 * Contract for location filtering algorithms.
 * These are provided by another team and integrated here based on car model.
 */
interface LocationFilterAlgorithm {
    val id: String

    /** Process a window/buffer of raw points and return the processed points. */
    fun process(raw: List<RawLocationPoint>): List<ProcessedLocationPoint>
}

/**
 * Simple demo algorithm for testing purposes.
 * Just returns the raw points as-is, tagging them with a demo ID.
 */
class DemoPassthroughAlgorithm : LocationFilterAlgorithm {
    override val id: String = "demo_passthrough"

    /**
     * Process a list of raw location points and return filtered/smoothed points.
     */
    override fun process(raw: List<RawLocationPoint>): List<ProcessedLocationPoint> {
        return raw.map {
            ProcessedLocationPoint(
                timestamp = it.timestamp,
                latitude = it.latitude,
                longitude = it.longitude,
                speedMps = it.speedMps,
                algorithmId = id
            )
        }
    }
}

