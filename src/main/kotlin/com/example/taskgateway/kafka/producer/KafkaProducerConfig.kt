package com.example.taskgateway.kafka.producer

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig(
        @Value("\${kafka.base-url}")
        val server: String,
        @Value("\${kafka.producer.topic}")
        val topic: String) {

    fun createConfig(): Map<String, Any> {
        return mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to server,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
    }

    fun createProducerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory<String, String>(createConfig())
    }

    @Bean
    fun createKafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(createProducerFactory())
    }

    @Bean
    fun createProducer(kafkaTemplate: KafkaTemplate<String, String>): KafkaProducer {
        return KafkaProducer(topic, kafkaTemplate)
    }

}