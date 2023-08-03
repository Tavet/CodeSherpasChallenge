package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserRequest
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.service.DispenserService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.BodyInserters


@ExtendWith(MockKExtension::class)
class DispenserControllerTest {

    private lateinit var webTestClient: WebTestClient

    @MockK
    lateinit var dispenserService: DispenserService

    @InjectMockKs
    lateinit var dispenserController: DispenserController

    private lateinit var dispenser: Dispenser

    @BeforeEach
    fun setup() {
        val dispenserId = "123"
        val flowVolume = 30.0
        val pricePerLiter = 12.0
        val status = StatusEnum.ON.value
        dispenser =
            Dispenser(id = dispenserId, flowVolume = flowVolume, pricePerLiter = pricePerLiter, status = status)

        webTestClient = WebTestClient.bindToController(dispenserController).build()
    }

    @Test
    fun `should return dispenser when valid id provided`() {
        every { dispenserService.findById("testId123") } returns Mono.just(dispenser)

        webTestClient.get()
            .uri("/api/dispenser/testId123")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                {"id":"123","flowVolume":30.0,"pricePerLiter":12.0,"status":"ON"}
            """
            )


        verify { dispenserService.findById("testId123") }
    }

    @Test
    fun `create dispenser returns dispenser`() {
        val request = DispenserRequest(12.0, 7.0)
        every { dispenserService.createDispenser(any()) } returns Mono.just(dispenser)

        webTestClient.post()
            .uri("/api/dispenser")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                {"id":"123","flowVolume":30.0,"pricePerLiter":12.0,"status":"ON"}
            """
            )

        verify { dispenserService.createDispenser(any()) }
    }
}