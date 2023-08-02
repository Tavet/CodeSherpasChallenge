package com.github.tavet.codesherpaschallenge.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Validated
@Component
@ConfigurationProperties(prefix = "mongodb")
class DatabaseProperties {

    @NotBlank(message = "Mongodb host is not present")
    var host: String? = null

    @NotBlank(message = "Mongodb port is not present")
    var port: String? = null

    @NotBlank(message = "Mongodb username is not present")
    var username: String? = null

    @NotBlank(message = "Mongodb password is not present")
    var password: String? = null

    @NotBlank(message = "Mongodb database name is not present")
    var dbName: String? = null

}