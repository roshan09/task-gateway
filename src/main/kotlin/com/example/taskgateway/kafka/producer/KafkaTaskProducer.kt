package com.example.taskgateway.kafka.producer

import com.example.taskgateway.Task
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaTaskProducer(
        @Value("\${kafka.producer.topic}")
        val topic: String,
        @Autowired
        private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun produce(task: Task) {
        val taskString = jacksonObjectMapper().writeValueAsString(task)
        kafkaTemplate.send(topic, taskString)
    }
}