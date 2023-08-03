package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/dispenser/report")
@RestController
class ReportController(private val reportService: ReportService) {

    @Operation(summary = "Gets the reports for all the dispensers", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllReports() = reportService.getAllDispenserReport()

    @Operation(summary = "Gets the reports for all the dispensers", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getSingleReports(@PathVariable id: String) = reportService.getDispenserReport(id)
}