package com.github.tavet.codesherpaschallenge.model.dispenser

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Schema(description = "Model for dispenser object")
@Document("dispensers")
data class Dispenser(
    @Id
    val id: String? = null,
    @field:Schema(
        description = "How many liters of beer come out per second",
        example = "0.1"
    )
    @Field("flow_volume")
    var flowVolume: Double,
    @field:Schema(
        description = "Price per litter",
        example = "0.40"
    )
    var pricePerLiter: Double,
    @field:Schema(
        description = "The status of the dispenser (ON/OFF)",
        example = "ON"
    )
    var status: String
)