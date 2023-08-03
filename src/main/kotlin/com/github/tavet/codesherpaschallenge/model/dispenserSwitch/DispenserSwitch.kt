package com.github.tavet.codesherpaschallenge.model.dispenserSwitch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Schema(description = "Model for each use (service) that is given to the dispenser. Save each time it is turned on until it is turned off")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("dispenser_services")
data class DispenserSwitch(
    @Id
    val id: String? = null,
    var dispenser: Dispenser?,
    var startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    @field:Schema(
        description = "Total seconds the tap was opened",
        example = "110"
    )
    var seconds: Long? = null,
    @field:Schema(
        description = "Total amount of liters dispensed in the service",
        example = "100"
    )
    var dispensed: Double? = null,
    @field:Schema(
        description = "Total cost for the amount of liters dispensed",
        example = "300"
    )
    var cost: Double? = null
)