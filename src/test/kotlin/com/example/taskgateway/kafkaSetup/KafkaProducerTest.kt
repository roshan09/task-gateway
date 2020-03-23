package com.example.taskgateway.kafkaSetup

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducerTest(
        @Autowired
        private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun produce(topic: String, eventString: String) {
        kafkaTemplate.send(topic, eventString)
    }
}