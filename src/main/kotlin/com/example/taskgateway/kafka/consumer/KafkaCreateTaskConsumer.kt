package com.example.taskgateway.kafka.consumer

import com.example.taskgateway.kafka.TaskService
import com.example.taskgateway.kafka.producer.KafkaTaskProducer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaCreateTaskConsumer(
        @Autowired
        val taskProducer: KafkaTaskProducer,
        @Autowired
        val taskService: TaskService
) {

    @KafkaListener(topics = ["\${kafka.consumer.topic}"], groupId = "\${kafka.consumer.group-id}")
    fun consume(event: String) {
        taskService.process(event)
    }
}