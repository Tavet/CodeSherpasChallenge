package com.github.tavet.codesherpaschallenge.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class DispenserSwitchException(message: String) : RuntimeException(message)