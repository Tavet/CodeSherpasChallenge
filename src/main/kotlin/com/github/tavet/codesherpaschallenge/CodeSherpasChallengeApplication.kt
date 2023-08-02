package com.github.tavet.codesherpaschallenge

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.github.tavet"])
@OpenAPIDefinition
@SpringBootApplication
class CodeSherpasChallengeApplication

fun main(args: Array<String>) {
	runApplication<CodeSherpasChallengeApplication>(*args)
}
