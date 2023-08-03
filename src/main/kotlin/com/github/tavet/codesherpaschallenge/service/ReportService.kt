package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitch
import com.github.tavet.codesherpaschallenge.model.dispenserSwitch.DispenserSwitchMetrics
import com.github.tavet.codesherpaschallenge.model.exception.ReportException
import com.github.tavet.codesherpaschallenge.model.report.ReportResponse
import com.github.tavet.codesherpaschallenge.util.CalcUtil.calculateDispenserMetrics
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ReportService(
    private val dispenserService: DispenserService,
    private val dispenserSwitchService: DispenserSwitchService
) {
    fun getAllDispenserReport(): Flux<ReportResponse> =
        dispenserService.findAll()
            .flatMap(this::generateReportForDispenser)
            .switchIfEmpty(Flux.error(ReportException("There are no dispensers, hence reports are not available")))

    fun getDispenserReport(id: String): Mono<ReportResponse> =
        dispenserService.findById(id)
            .flatMap(this::generateReportForDispenser)
            .switchIfEmpty(Mono.error(ReportException("There requested dispenser doesn't exist, hence reports are not available")))

    private fun generateReportForDispenser(dispenser: Dispenser): Mono<ReportResponse> =
        dispenserSwitchService.findAll(dispenser.id)
            .collectList()
            .map { dispenserSwitchList -> createReportResponse(dispenser, dispenserSwitchList) }

    private fun createReportResponse(
        dispenser: Dispenser,
        dispenserSwitchList: List<DispenserSwitch>
    ): ReportResponse {
        var totalDispensedLiters = 0.0
        var totalCost = 0.0
        var totalSeconds = 0L

        dispenserSwitchList.map { switch ->
            val metrics = getMetrics(switch)
            totalDispensedLiters += metrics.totalDispensed ?: 0.0
            totalCost += metrics.totalCost ?: 0.0
            totalSeconds += metrics.totalSeconds ?: 0
            switch.dispenser = null
        }

        return ReportResponse(
            dispenser,
            dispenserSwitchList.size,
            totalSeconds,
            totalDispensedLiters,
            totalCost,
            dispenserSwitchList
        )
    }

    private fun getMetrics(switch: DispenserSwitch): DispenserSwitchMetrics {
        var endTime: LocalDateTime? = switch.endTime
        if (endTime == null) {
            val metrics = calculateDispenserMetrics(switch)
            switch.dispensed = metrics.totalDispensed
            switch.seconds = metrics.totalSeconds
            switch.cost = metrics.totalCost
            return metrics
        }
        return DispenserSwitchMetrics(switch.seconds, switch.dispensed, switch.cost)
    }

}