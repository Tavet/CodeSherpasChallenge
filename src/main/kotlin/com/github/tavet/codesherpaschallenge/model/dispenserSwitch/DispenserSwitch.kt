package com.github.tavet.codesherpaschallenge.model.dispenserSwitch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("dispenser_sessions")
data class DispenserSwitch(
    @Id
    val id: String? = null,
    var dispenser: Dispenser?,
    var startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    var dispensed: Double? = null,
    var cost: Double? = null
)