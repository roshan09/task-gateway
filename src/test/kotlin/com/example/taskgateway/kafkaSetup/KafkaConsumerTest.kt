package com.example.taskgateway.kafkaSetup

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class KafkaConsumerTest {

    private val tasks = mutableListOf<String>()

    @KafkaListener(topics = ["\${kafka.producer.topic}"], groupId = "dummy-group-id")
    fun consume(event: String) {
        tasks.add(event)
    }

    fun findAllTasks(): MutableList<String> {
        return tasks
    }

    fun clear() {
        tasks.clear()
    }
}