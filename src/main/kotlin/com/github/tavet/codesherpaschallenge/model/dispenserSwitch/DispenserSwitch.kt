package com.github.tavet.codesherpaschallenge.model.dispenserSwitch

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

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