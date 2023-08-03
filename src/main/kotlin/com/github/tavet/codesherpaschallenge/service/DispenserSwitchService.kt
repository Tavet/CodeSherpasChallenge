package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchMetrics
import com.github.tavet.codesherpaschallenge.model.exception.NotFoundException
import com.github.tavet.codesherpaschallenge.repository.DispenserSwitchRepository
import com.github.tavet.codesherpaschallenge.util.CalcUtil.calculateDispenserMetrics
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime

@Service
class DispenserSwitchService(
    private val dispenserSwitchRepository: DispenserSwitchRepository
) {

    fun findAll(dispenserId: String?): Flux<DispenserSwitch> = dispenserSwitchRepository.findByDispenserId(dispenserId)

    fun findByDispenserIdAndEndTimeIsNull(id: String?): Mono<DispenserSwitch> =
        dispenserSwitchRepository.findByDispenserIdAndEndTimeIsNull(id)
            .switchIfEmpty { Mono.error(NotFoundException("Dispenser service with Dispenser ID $id not found")) }

    fun switchDispenserOn(dispenser: Dispenser) = dispenserSwitchRepository.save(
        DispenserSwitch(
            startTime = LocalDateTime.now(),
            dispenser = dispenser
        )
    )

    fun switchDispenserOff(dispenserReq: Dispenser) = findByDispenserIdAndEndTimeIsNull(dispenserReq.id)
        .flatMap {
            var calcEndTime: LocalDateTime = LocalDateTime.now()
            var metrics: DispenserSwitchMetrics = calculateDispenserMetrics(it)

            dispenserSwitchRepository.save(
                it.apply {
                    dispenser = dispenserReq
                    endTime = calcEndTime
                    dispensed = metrics.totalDispensed
                    cost = metrics.totalCost
                    seconds = metrics.totalSeconds
                }
            )
        }
}