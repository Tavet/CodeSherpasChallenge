package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenser.DispenserRequest
import com.github.tavet.codesherpaschallenge.service.DispenserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RequestMapping("/api/dispenser")
@RestController
class DispenserController(private val dispenserService: DispenserService) {

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getDispenser(@PathVariable id: String): Mono<Dispenser> =
        dispenserService.findById(id)

    @PostMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveDispenser(@RequestBody request: DispenserRequest): Mono<Dispenser>  =
        dispenserService.createDispenser(request)
}