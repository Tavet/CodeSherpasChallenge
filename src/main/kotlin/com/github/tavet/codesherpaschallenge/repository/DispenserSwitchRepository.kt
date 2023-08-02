package com.github.tavet.codesherpaschallenge.repository

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface DispenserSwitchRepository: ReactiveMongoRepository<DispenserSwitch, String> {
    fun findByDispenserIdAndEndTimeIsNull(id: String?): Mono<DispenserSwitch>
}
