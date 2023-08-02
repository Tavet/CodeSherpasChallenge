package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchOffRequest
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchRequest
import com.github.tavet.codesherpaschallenge.service.DispenserService
import com.github.tavet.codesherpaschallenge.service.DispenserSwitchService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/dispenser/session")
@RestController
class DispenserSwitchController(private val dispenserService: DispenserService) {

    @PatchMapping("/on")
    fun dispenserSwitchOn(@RequestBody request: DispenserSwitchRequest) =
        dispenserService.switchDispenserOn(request)

    @PatchMapping("/off")
    fun dispenserSwitchOff(@RequestBody request: DispenserSwitchRequest) =
        dispenserService.switchDispenserOff(request)

}