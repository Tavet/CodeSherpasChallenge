package com.github.tavet.codesherpaschallenge.repository

import com.github.tavet.codesherpaschallenge.model.dispenser.Dispenser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface DispenserRepository: ReactiveMongoRepository<Dispenser, String>
