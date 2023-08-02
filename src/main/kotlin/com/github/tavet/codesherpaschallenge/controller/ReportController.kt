package com.github.tavet.codesherpaschallenge.controller

import com.github.tavet.codesherpaschallenge.model.report.ReportResponse
import com.github.tavet.codesherpaschallenge.service.ReportService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RequestMapping("/api/dispenser/report")
@RestController
class ReportController(private val reportService: ReportService) {

    @GetMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getAllReports() = reportService.getAllDispenserReport()
}