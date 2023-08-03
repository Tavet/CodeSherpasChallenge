package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.report.ReportResponse
import com.github.tavet.codesherpaschallenge.service.ReportService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@ExtendWith(MockKExtension::class)
class ReportControllerTest {
    private lateinit var webTestClient: WebTestClient

    @MockK
    lateinit var reportService: ReportService

    @InjectMockKs
    lateinit var reportController: ReportController

    private lateinit var report: ReportResponse

    @BeforeEach
    fun setup() {
        val dispenserId = "123"
        val flowVolume = 30.0
        val pricePerLiter = 12.0
        val status = StatusEnum.ON.value
        val dispenser: Dispenser =
            Dispenser(id = dispenserId, flowVolume = flowVolume, pricePerLiter = pricePerLiter, status = status)
        val timesUsed: Int? = 5
        val secondsUsed: Long? = 10L
        val litersDispensed: Double? = 50.0
        val totalCost: Double? = 165.238
        val services: List<DispenserSwitch>? = null

        report = ReportResponse(dispenser, timesUsed, secondsUsed, litersDispensed, totalCost, services)

        webTestClient = WebTestClient.bindToController(reportController).build()
    }

    @Test
    fun `report should get the reports for all the dispensers`() {
        every { reportService.getAllDispenserReport() } returns Flux.just(report)

        webTestClient.get()
            .uri("/api/dispenser/report")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                [{"dispenser":{"id":"123",
                "flowVolume":30.0,"pricePerLiter":12.0,
                "status":"ON"},"timesUsed":5,"secondsUsed":10,
                "litersDispensed":50.0,"totalCost":165.238}]
            """
            )

        verify { reportService.getAllDispenserReport() }
    }
}