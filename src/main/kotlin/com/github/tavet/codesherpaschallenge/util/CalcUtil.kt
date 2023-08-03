package com.github.tavet.codesherpaschallenge.util

import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchMetrics
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object CalcUtil {
    private fun calculateTotalTimeInSeconds(start: LocalDateTime, end: LocalDateTime): Long =
        ChronoUnit.SECONDS.between(start, end)

    private fun calculateTotalCost(totalDispensed: Double, pricePerLiter: Double) =
        totalDispensed * pricePerLiter

    private fun calculateTotalDispensed(flowVolume: Double, dispenseTime: Long): Double =
        flowVolume.times(dispenseTime)

    fun calculateDispenserMetrics(dispenserSwitch: DispenserSwitch): DispenserSwitchMetrics {
        var endTime: LocalDateTime? = dispenserSwitch.endTime

        if (endTime == null) {
            endTime = LocalDateTime.now()
        }

        var calcTotalSeconds: Long = CalcUtil.calculateTotalTimeInSeconds(dispenserSwitch.startTime!!, endTime!!)
        var calcDispensed: Double =
            CalcUtil.calculateTotalDispensed(dispenserSwitch.dispenser!!.flowVolume, calcTotalSeconds)
        var calcTotalCost: Double =
            CalcUtil.calculateTotalCost(calcDispensed, dispenserSwitch.dispenser!!.pricePerLiter)

        return DispenserSwitchMetrics(
            calcTotalSeconds,
            calcDispensed,
            calcTotalCost
        )
    }

}