package com.example.taskgateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class TaskConfiguration(
        @Autowired
        val resourceLoader: ResourceLoader
) {
    val contracts = resourceLoader.getResource("classpath:contract/task-contract.json")

    @Bean
    fun createTaskContract(): List<TaskContract> {
        val contractJson = contracts.file.readText()
        return jacksonObjectMapper().readValue(contractJson)
    }
}