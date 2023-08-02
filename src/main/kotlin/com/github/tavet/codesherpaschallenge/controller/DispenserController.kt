package com.github.tavet.codesherpaschallenge.controller

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

@RequestMapping("/api/dispenser")
@RestController
class DispenserController(private val dispenserService: DispenserService) {

    @GetMapping("/{id}")
    fun getDispenser(@PathVariable id: String) =
        dispenserService.findById(id)

    @PostMapping
    fun saveDispenser(@RequestBody request: DispenserRequest) =
        dispenserService.createDispenser(request)
}