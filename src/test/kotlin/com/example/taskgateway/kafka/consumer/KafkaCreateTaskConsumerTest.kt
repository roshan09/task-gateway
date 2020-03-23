package com.example.taskgateway.kafka.consumer

import com.example.taskgateway.kafka.TaskService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test


class KafkaCreateTaskConsumerTest {

    @Test
    fun `should process event`() {
        val mockTaskService = mockk<TaskService>()
        val kafkaCreateTaskConsumer = KafkaCreateTaskConsumer(mockTaskService)

        val eventString = "dummyEventString"

        every { mockTaskService.process(eventString) } returns Unit

        kafkaCreateTaskConsumer.consume(eventString)

        verify(exactly = 1) { mockTaskService.process(eventString) }
    }

}