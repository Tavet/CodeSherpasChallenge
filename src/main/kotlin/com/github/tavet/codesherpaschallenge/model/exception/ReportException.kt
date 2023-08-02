package com.github.tavet.codesherpaschallenge.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ReportException(message: String) : RuntimeException(message)