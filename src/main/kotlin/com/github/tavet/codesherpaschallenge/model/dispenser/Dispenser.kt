package com.github.tavet.codesherpaschallenge.model.dispenser

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("dispensers")
data class Dispenser(
    @Id
    val id: String? = null,
    @Field("flow_volume")
    var flowVolume: Double,
    var pricePerLiter: Double,
    var status: String
)