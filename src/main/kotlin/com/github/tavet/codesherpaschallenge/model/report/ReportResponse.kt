package com.github.tavet.codesherpaschallenge.model.report

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for reports. Contains a dispenser per object, total consumption, total price, total seconds used, and a list of dispenser services")
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