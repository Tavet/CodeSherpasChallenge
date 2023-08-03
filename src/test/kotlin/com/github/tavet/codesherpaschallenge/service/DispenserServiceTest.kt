package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserRequest
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchRequest
import com.github.tavet.codesherpaschallenge.model.exception.NotFoundException
import com.github.tavet.codesherpaschallenge.repository.DispenserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DispenserServiceTest {

    @MockK
    lateinit var dispenserRepository: DispenserRepository

    @MockK
    lateinit var dispenserSwitchService: DispenserSwitchService

    @InjectMockKs
    lateinit var dispenserService: DispenserService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test findById when Dispenser exists`() {
        val dispenserId = "1"
        val dispenser = Dispenser(id = "1", flowVolume = 12.0, pricePerLiter = 7.0, status = StatusEnum.OFF.value)
        every { dispenserRepository.findById(dispenserId) } returns Mono.just(dispenser)

        val result = dispenserService.findById(dispenserId)

        StepVerifier.create(result)
            .expectNext(dispenser)
            .verifyComplete()

        verify { dispenserRepository.findById(dispenserId) }
    }

    @Test
    fun `test findAll when Dispensers exist`() {
        val dispenser1 = Dispenser(id = "1", flowVolume = 12.0, pricePerLiter = 7.0, status = StatusEnum.OFF.value)
        val dispenser2 = Dispenser(id = "2", flowVolume = 12.0, pricePerLiter = 7.0, status = StatusEnum.OFF.value)

        every { dispenserRepository.findAll() } returns Flux.just(dispenser1, dispenser2)

        val result = dispenserService.findAll()

        StepVerifier.create(result)
            .expectNext(dispenser1, dispenser2)
            .verifyComplete()

        verify { dispenserRepository.findAll() }
    }

    @Test
    fun `test createDispenser`() {
        val dispenserRequest = DispenserRequest(flowVolume = 7.0, pricePerLiter = 12.0)
        val dispenser = Dispenser(
            flowVolume = dispenserRequest.flowVolume,
            pricePerLiter = dispenserRequest.pricePerLiter,
            status = StatusEnum.OFF.value
        )

        every { dispenserRepository.save(any()) } returns Mono.just(dispenser)

        val result = dispenserService.createDispenser(dispenserRequest)

        StepVerifier.create(result)
            .expectNext(dispenser)
            .verifyComplete()

        verify { dispenserRepository.save(any()) }
    }

    @Test
    fun `test switchDispenser when status is ON`() {
        val dispenserId = "1"
        val dispenser = Dispenser(
            id = "1",
            flowVolume = 10.0,
            pricePerLiter = 5.0,
            status = StatusEnum.ON.value
        )
        val dispenserSwitchRequest = DispenserSwitchRequest(dispenserId = dispenser.id!!)

        every { dispenserRepository.findById(dispenserId) } returns Mono.just(dispenser)
        every { dispenserSwitchService.switchDispenserOff(any()) } returns Mono.just(DispenserSwitch(startTime = LocalDateTime.now(), dispenser = dispenser))
        every { dispenserRepository.save(any()) } returns Mono.just(dispenser)

        val result = dispenserService.switchDispenser(dispenserSwitchRequest)

        StepVerifier.create(result)
            .expectNext(dispenser)
            .verifyComplete()

        verify { dispenserRepository.findById(dispenserId) }
        verify { dispenserSwitchService.switchDispenserOff(any()) }
        verify { dispenserRepository.save(any()) }
    }

    @Test
    fun `test switchDispenser when status is OFF`() {
        val dispenserId = "1"
        val dispenser = Dispenser(
            id = "1",
            flowVolume = 10.0,
            pricePerLiter = 5.0,
            status = StatusEnum.OFF.value
        )
        val dispenserSwitchRequest = DispenserSwitchRequest(dispenserId = dispenser.id!!)

        every { dispenserRepository.findById(dispenserId) } returns Mono.just(dispenser)
        every { dispenserSwitchService.switchDispenserOn(any()) } returns Mono.just(DispenserSwitch(startTime = LocalDateTime.now(), dispenser = dispenser))
        every { dispenserRepository.save(any()) } returns Mono.just(dispenser)

        val result = dispenserService.switchDispenser(dispenserSwitchRequest)

        StepVerifier.create(result)
            .expectNext(dispenser)
            .verifyComplete()

        verify { dispenserRepository.findById(dispenserId) }
        verify { dispenserSwitchService.switchDispenserOn(any()) }
        verify { dispenserRepository.save(any()) }
    }

    @Test
    fun `test switchDispenser when dispenser not found`() {
        val dispenserId = "1"
        val dispenserSwitchRequest = DispenserSwitchRequest(dispenserId = "1")

        every { dispenserRepository.findById(dispenserId) } returns Mono.empty()

        val result = dispenserService.switchDispenser(dispenserSwitchRequest)

        StepVerifier.create(result)
            .expectError(NotFoundException::class.java)
            .verify()

        verify { dispenserRepository.findById(dispenserId) }
    }
}