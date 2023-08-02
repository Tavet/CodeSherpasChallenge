package com.github.tavet.codesherpaschallenge.service

import com.github.tavet.codesherpaschallenge.model.exception.ReportException
import com.github.tavet.codesherpaschallenge.model.report.ReportResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class ReportService(
    private val dispenserService: DispenserService,
    private val dispenserSwitchService: DispenserSwitchService
) {
    fun getAllDispenserReport(): Flux<ReportResponse> =
        dispenserService.findAll().flatMap { dispenser ->
            dispenserSwitchService
                .findAll(dispenser.id)
                .collectList().map { dispenserSwitchList ->
                    var totalDispensedLiters = 0.0
                    var totalCost = 0.0
                    var totalSeconds = 0L
                    dispenserSwitchList.map { switch ->
                        totalDispensedLiters += switch.dispensed ?: 0.0
                        totalCost += switch.cost ?: 0.0
                        // If no end time, then get current time to calculate how many seconds the tap has spent til now.
                        totalSeconds += (switch.endTime?.toEpochSecond(ZoneOffset.UTC) ?: LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.UTC)) - (switch.startTime?.toEpochSecond(ZoneOffset.UTC) ?: 0)
                        switch.dispenser = null
                    }
                    ReportResponse(
                        dispenser,
                        dispenserSwitchList.size,
                        totalSeconds,
                        totalDispensedLiters,
                        totalCost,
                        dispenserSwitchList
                    )

                }
        }.switchIfEmpty(Flux.error(ReportException("There are no dispensers, hence reports are not available")))

}