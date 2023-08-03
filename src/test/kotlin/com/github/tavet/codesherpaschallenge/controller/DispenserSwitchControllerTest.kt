package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserResponse
import com.github.tavet.codesherpaschallenge.model.dispenser.StatusEnum
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchRequest
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
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono

@ExtendWith(MockKExtension::class)
class DispenserSwitchControllerTest {
    private lateinit var webTestClient: WebTestClient

    @MockK
    lateinit var dispenserSwitchService: DispenserService

    @InjectMockKs
    lateinit var dispenserSwitchController: DispenserSwitchController

    private lateinit var dispenser: DispenserResponse

    @BeforeEach
    fun setup() {
        val dispenserId = "123"
        val flowVolume = 30.0
        val pricePerLiter = 12.0
        val status = StatusEnum.ON.value
        dispenser =
            DispenserResponse(id = dispenserId, flowVolume = flowVolume, pricePerLiter = pricePerLiter, status = status)

        webTestClient = WebTestClient.bindToController(dispenserSwitchController).build()
    }

    @Test
    fun `switch dispenser returns success`() {
        val request = DispenserSwitchRequest("1")
        every { dispenserSwitchService.switchDispenser(any()) } returns Mono.just(dispenser)

        webTestClient.patch()
            .uri("/api/dispenser/switch")
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

        verify { dispenserSwitchService.switchDispenser(any()) }
    }
}