package com.example.taskgateway.kafka.consumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["\${kafka.consumer.topic}"], groupId = "\${kafka.consumer.group-id}")
    fun consume() {

    }
}