package com.github.tavet.codesherpaschallenge.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
@EnableConfigurationProperties(DatabaseProperties::class)
class DatabaseConfig(private val databaseProperties: DatabaseProperties) : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName(): String {
        return databaseProperties.dbName.orEmpty()
    }

    @Bean
    override fun reactiveMongoClient(): MongoClient {

        return MongoClients.create(
            String.format(
                "mongodb://%s:%s@%s:%s",
                databaseProperties.username,
                databaseProperties.password,
                databaseProperties.host,
                databaseProperties.port
            )
        )
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), databaseName)
    }

}