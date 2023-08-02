package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchOnRequest
import com.github.tavet.codesherpaschallenge.service.DispenserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/dispenser/session")
@RestController
class DispenserSwitchController(private val dispenserService: DispenserService) {

    @PatchMapping("/on", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun dispenserSwitchOn(@RequestBody request: DispenserSwitchOnRequest) =
        dispenserService.switchDispenserOn(request)

    @PatchMapping("/off", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun dispenserSwitchOff(@RequestBody request: DispenserSwitchOnRequest) =
        dispenserService.switchDispenserOff(request)

}