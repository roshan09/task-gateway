package com.example.taskgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskGatewayApplication

fun main(args: Array<String>) {
	runApplication<TaskGatewayApplication>(*args)
}
