package com.github.tavet.codesherpaschallenge.model.dispenser

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for creating a dispenser")
class DispenserRequest(val flowVolume: Double, val pricePerLiter: Double)