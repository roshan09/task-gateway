package com.example.taskgateway.kafka.producer

import com.example.taskgateway.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.kafka.core.KafkaTemplate

class KafkaTaskProducerTest {

    @Test
    fun `should produce event`() {

        val mockKafkaTemplate = mockk<KafkaTemplate<String, String>>()
        val topic = "dummy-topic"
        val kafkaProducer = KafkaTaskProducer(topic, mockKafkaTemplate)

        val task = Task("dummy-task-type", mapOf("key" to "value"))
        val taskString = """{"taskType":"dummy-task-type","payload":{"key":"value"}}"""

        every { mockKafkaTemplate.send(topic, taskString) } returns mockk()

        kafkaProducer.produce(task)

        verify(exactly = 1) { mockKafkaTemplate.send(topic, taskString) }
    }

}