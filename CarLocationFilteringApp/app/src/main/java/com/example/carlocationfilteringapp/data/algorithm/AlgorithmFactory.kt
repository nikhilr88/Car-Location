package com.example.carlocationfilteringapp.data.algorithm

import com.example.carlocationfilteringapp.data.model.CarModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory to map car models to their respective filtering algorithms.
 * This logic can be extended or overridden based on business rules.
 **/
@Singleton
class AlgorithmFactory @Inject constructor() {
    fun forCarModel(model: CarModel): LocationFilterAlgorithm = when (model) {
        CarModel.MODEL_A -> DemoPassthroughAlgorithm() //Algorithm for MODEL_A
        CarModel.MODEL_B -> DemoPassthroughAlgorithm() //Algorithm for MODEL_B
        CarModel.MODEL_C -> DemoPassthroughAlgorithm() //Algorithm for MODEL_C
    }
}
