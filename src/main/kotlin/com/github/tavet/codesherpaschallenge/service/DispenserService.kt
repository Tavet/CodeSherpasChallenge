package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserRequest
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserResponse
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchMetrics
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchRequest
import com.github.tavet.codesherpaschallenge.model.exception.DispenserSwitchException
import com.github.tavet.codesherpaschallenge.model.exception.NotFoundException
import com.github.tavet.codesherpaschallenge.repository.DispenserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class DispenserService(
    private val dispenserRepository: DispenserRepository,
    private val dispenserSwitchService: DispenserSwitchService
) {
    fun findById(id: String): Mono<Dispenser> = dispenserRepository.findById(id)
        .switchIfEmpty { Mono.error(NotFoundException("Dispenser with ID $id not found")) }

    fun findAll(): Flux<Dispenser> = dispenserRepository.findAll()

    fun createDispenser(request: DispenserRequest): Mono<Dispenser> = dispenserRepository.save(
        Dispenser(
            flowVolume = request.flowVolume,
            pricePerLiter = request.pricePerLiter,
            status = StatusEnum.OFF.value
        )
    )

    fun switchDispenser(request: DispenserSwitchRequest): Mono<DispenserResponse> = findById(request.dispenserId)
        .flatMap {
            if (StatusEnum.valueOf(it.status) == StatusEnum.ON) {
                switchDispenserOff(it)
            } else {
                switchDispenserOn(it)
            }
        }

    private fun switchDispenserOn(dispenser: Dispenser): Mono<DispenserResponse> {
        return if (StatusEnum.valueOf(dispenser.status) == StatusEnum.ON) {
            Mono.error(DispenserSwitchException("The dispenser tap is already open"))
        } else {
            dispenserRepository.save(
                dispenser.apply {
                    status = StatusEnum.ON.value
                }
            ).flatMap { dispenser ->
                dispenserSwitchService.switchDispenserOn(dispenser).map {
                    DispenserResponse(dispenser.id, dispenser.flowVolume, dispenser.pricePerLiter, dispenser.status)
                }
            }
        }
    }

    private fun switchDispenserOff(dispenser: Dispenser): Mono<DispenserResponse> {
        return if (StatusEnum.valueOf(dispenser.status) == StatusEnum.OFF) {
            Mono.error(DispenserSwitchException("The dispenser tap is already closed"))
        } else {
            dispenserRepository.save(
                dispenser.apply {
                    status = StatusEnum.OFF.value
                }
            ).flatMap {
                dispenserSwitchService.switchDispenserOff(it).map {
                    it.dispenser = null
                    DispenserResponse(dispenser.id, dispenser.flowVolume, dispenser.pricePerLiter, dispenser.status, it)
                }
            }
        }
    }
}