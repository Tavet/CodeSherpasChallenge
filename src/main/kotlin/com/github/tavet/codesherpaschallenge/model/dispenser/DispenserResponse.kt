package com.github.tavet.codesherpaschallenge.model.dispenser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DispenserResponse (
    val id: String? = null,
    val flowVolume: Double,
    val pricePerLiter: Double,
    val status: String,
    val service: DispenserSwitch? = null
)