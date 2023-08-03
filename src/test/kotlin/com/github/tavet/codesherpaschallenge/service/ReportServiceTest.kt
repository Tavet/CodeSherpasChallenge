package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.exception.ReportException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ReportServiceTest {
    @MockK
    lateinit var dispenserService: DispenserService

    @MockK
    lateinit var dispenserSwitchService: DispenserSwitchService

    @InjectMockKs
    lateinit var reportService: ReportService

    private val dispenser = Dispenser(
        id = "1",
        flowVolume = 10.0,
        pricePerLiter = 5.0,
        status = StatusEnum.ON.value
    )

    private val dispenserSwitch = DispenserSwitch(
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        dispenser = dispenser
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getAllDispenserReport with dispensers`() {
        every { dispenserService.findAll() } returns Flux.just(dispenser)
        every { dispenserSwitchService.findAll(any()) } returns Flux.just(dispenserSwitch)

        val result = reportService.getAllDispenserReport()

        StepVerifier.create(result)
            .expectNextCount(1)
            .verifyComplete()

        verify { dispenserService.findAll() }
        verify { dispenserSwitchService.findAll(any()) }
    }

    @Test
    fun `test getAllDispenserReport without dispensers`() {
        every { dispenserService.findAll() } returns Flux.empty()

        val result = reportService.getAllDispenserReport()

        StepVerifier.create(result)
            .expectError(ReportException::class.java)
            .verify()

        verify { dispenserService.findAll() }
    }

    @Test
    fun `test getDispenserReport with dispenser`() {
        every { dispenserService.findById(any()) } returns Mono.just(dispenser)
        every { dispenserSwitchService.findAll(any()) } returns Flux.just(dispenserSwitch)

        val result = reportService.getDispenserReport("1")

        StepVerifier.create(result)
            .expectNextCount(1)
            .verifyComplete()

        verify { dispenserService.findById(any()) }
        verify { dispenserSwitchService.findAll(any()) }
    }

    @Test
    fun `test getDispenserReport without dispenser`() {
        every { dispenserService.findById(any()) } returns Mono.empty()

        val result = reportService.getDispenserReport("1")

        StepVerifier.create(result)
            .expectError(ReportException::class.java)
            .verify()

        verify { dispenserService.findById(any()) }
    }
}