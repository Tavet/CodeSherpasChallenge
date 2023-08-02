package com.github.tavet.codesherpaschallenge.model.report

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class ReportResponse(
    val dispenser: Dispenser,
    val timesUsed: Int?,
    val secondsUsed: Long?,
    val litersDispensed: Double?,
    val totalMoney: Double?,
    val services: List<DispenserSwitch>?
)