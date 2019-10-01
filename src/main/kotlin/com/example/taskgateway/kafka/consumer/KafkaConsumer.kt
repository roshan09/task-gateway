package com.example.taskgateway.kafka.consumer

import com.example.taskgateway.kafka.producer.KafkaProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
        @Autowired
        val producer : KafkaProducer
) {

    @KafkaListener(topics = ["\${kafka.consumer.topic}"], groupId = "\${kafka.consumer.group-id}")
    fun consume(event :String) {
        println("event received $event")
        producer.produce(event)
    }
}