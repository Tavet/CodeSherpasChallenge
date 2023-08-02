package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchRequest
import com.github.tavet.codesherpaschallenge.service.DispenserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/dispenser/switch")
@RestController
class DispenserSwitchController(private val dispenserService: DispenserService) {

    @Operation(summary = "Switch OFF/ON a dispenser", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    @PatchMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun dispenserSwitchOn(@RequestBody request: DispenserSwitchRequest) =
        dispenserService.switchDispenser(request)

}