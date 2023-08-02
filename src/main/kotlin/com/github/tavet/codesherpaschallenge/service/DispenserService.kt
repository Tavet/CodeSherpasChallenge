package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserRequest
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.exception.NotFoundException
import com.github.tavet.codesherpaschallenge.repository.DispenserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class DispenserService(private val dispenserRepository: DispenserRepository) {
    fun findById(id: String): Mono<Dispenser> = dispenserRepository.findById(id)
        .switchIfEmpty { Mono.error(NotFoundException("Dispenser with ID $id not found")) }

    fun createDispenser(request: DispenserRequest): Mono<Dispenser> = dispenserRepository.save(
        Dispenser(
            flowVolume = request.flowVolume, status = StatusEnum.CLOSED.value
        )
    )

}