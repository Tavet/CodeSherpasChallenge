package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.exception.NotFoundException
import com.github.tavet.codesherpaschallenge.repository.DispenserSwitchRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class DispenserSwitchService(
    private val dispenserSwitchRepository: DispenserSwitchRepository
) {

    fun findAll(dispenserId: String?): Flux<DispenserSwitch> = dispenserSwitchRepository.findByDispenserId(dispenserId)

    fun findByDispenserIdAndEndTimeIsNull(id: String?): Mono<DispenserSwitch> =
        dispenserSwitchRepository.findByDispenserIdAndEndTimeIsNull(id)
            .switchIfEmpty { Mono.error(NotFoundException("Dispenser session with Dispenser ID $id not found")) }

    fun switchDispenserOn(dispenser: Dispenser) = dispenserSwitchRepository.save(
        DispenserSwitch(
            startTime = LocalDateTime.now(),
            dispenser = dispenser
        )
    )

    fun switchDispenserOff(dispenserReq: Dispenser) = findByDispenserIdAndEndTimeIsNull(dispenserReq.id)
        .flatMap {
            var calcEndTime: LocalDateTime = LocalDateTime.now()
            var calcTotalTime: Long = calculateTotalTimeInSeconds(it.startTime, calcEndTime)
            var calcDispensed: Double = calculateTotalDispensed(it.dispenser!!.flowVolume, calcTotalTime)

            dispenserSwitchRepository.save(
                it.apply {
                    dispenser = dispenserReq
                    endTime = calcEndTime
                    dispensed = calcDispensed
                    cost = calculateTotalCost(calcDispensed, it.dispenser!!.pricePerLiter)
                }
            )
        }

    private fun calculateTotalTimeInSeconds(start: LocalDateTime?, end: LocalDateTime): Long =
        ChronoUnit.SECONDS.between(start, end)

    private fun calculateTotalCost(totalDispensed: Double, pricePerLiter: Double) =
        totalDispensed * pricePerLiter

    private fun calculateTotalDispensed(flowVolume: Double, dispenseTime: Long): Double =
        flowVolume.times(dispenseTime)
}