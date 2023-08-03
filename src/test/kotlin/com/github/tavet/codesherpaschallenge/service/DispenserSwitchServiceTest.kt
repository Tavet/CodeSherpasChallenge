package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.exception.NotFoundException
import com.github.tavet.codesherpaschallenge.repository.DispenserSwitchRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DispenserSwitchServiceTest {
    @Mock
    private lateinit var dispenserSwitchRepository: DispenserSwitchRepository

    private lateinit var dispenserSwitchService: DispenserSwitchService

    @Test
    fun `findAll should return all DispenserSwitches for a given Dispenser ID`() {
        val dispenserId = "1"
        val dispenserSwitches = listOf(
            DispenserSwitch(id = "123", dispenser = null),
            DispenserSwitch(id = "456", dispenser = null)
        )

        `when`(dispenserSwitchRepository.findByDispenserId(dispenserId)).thenReturn(Flux.fromIterable(dispenserSwitches))

        dispenserSwitchService = DispenserSwitchService(dispenserSwitchRepository)
        val result = dispenserSwitchService.findAll(dispenserId)

        StepVerifier.create(result)
            .expectNextCount(2)
            .verifyComplete()
    }

    @Test
    fun `findByDispenserIdAndEndTimeIsNull should return DispenserSwitch for a given ID`() {
        val dispenserId = "1"
        val dispenserSwitch = DispenserSwitch(id = "12", dispenser = null)

        `when`(dispenserSwitchRepository.findByDispenserIdAndEndTimeIsNull(dispenserId)).thenReturn(
            Mono.just(
                dispenserSwitch
            )
        )

        dispenserSwitchService = DispenserSwitchService(dispenserSwitchRepository)
        val result = dispenserSwitchService.findByDispenserIdAndEndTimeIsNull(dispenserId)

        StepVerifier.create(result)
            .expectNext(dispenserSwitch)
            .verifyComplete()
    }

    @Test
    fun `findByDispenserIdAndEndTimeIsNull should throw NotFoundException when ID does not exist`() {
        val dispenserId = "1"

        `when`(dispenserSwitchRepository.findByDispenserIdAndEndTimeIsNull(dispenserId)).thenReturn(Mono.empty())

        dispenserSwitchService = DispenserSwitchService(dispenserSwitchRepository)
        val result = dispenserSwitchService.findByDispenserIdAndEndTimeIsNull(dispenserId)

        StepVerifier.create(result)
            .expectError(NotFoundException::class.java)
            .verify()
    }

    @Test
    fun `switchDispenserOff should update the DispenserSwitch and return the updated instance`() {
        val dispenserReq = Dispenser(id = "1", flowVolume = 12.0, pricePerLiter = 7.0, status = StatusEnum.OFF.value)
        val initialDispenserSwitch = DispenserSwitch(startTime = LocalDateTime.now(), dispenser = dispenserReq)
        val updatedDispenserSwitch = initialDispenserSwitch.copy(
            endTime = LocalDateTime.now(),
            dispensed = 12.0,
            cost = 7.0,
            seconds = 500
        )

        `when`(dispenserSwitchRepository.findByDispenserIdAndEndTimeIsNull(any())).thenReturn(
            Mono.just(
                initialDispenserSwitch
            )
        )
        `when`(dispenserSwitchRepository.save(any())).thenReturn(Mono.just(updatedDispenserSwitch))

        dispenserSwitchService = DispenserSwitchService(dispenserSwitchRepository)
        val result = dispenserSwitchService.switchDispenserOff(dispenserReq)

        StepVerifier.create(result)
            .expectNext(updatedDispenserSwitch)
            .verifyComplete()
    }

    @Test
    fun `switchDispenserOn should create a new DispenserSwitch and return it`() {
        val dispenserReq = Dispenser(id = "1", flowVolume = 12.0, pricePerLiter = 7.0, status = StatusEnum.OFF.value)
        val newDispenserSwitch = DispenserSwitch(startTime = LocalDateTime.now(), dispenser = dispenserReq)

        `when`(dispenserSwitchRepository.save(any(DispenserSwitch::class.java))).thenReturn(Mono.just(newDispenserSwitch))

        dispenserSwitchService = DispenserSwitchService(dispenserSwitchRepository)
        val result = dispenserSwitchService.switchDispenserOn(dispenserReq)

        StepVerifier.create(result)
            .expectNext(newDispenserSwitch)
            .verifyComplete()
    }
}