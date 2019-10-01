package com.example.taskgateway.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate

class KafkaProducer(
        private val topic: String,
        private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun produce(event: String) {
//        val eventJson = ObjectMapper().writeValueAsString(event)
        kafkaTemplate.send(topic, event)
    }
}