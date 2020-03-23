package com.example.taskgateway

import com.example.taskgateway.kafka.TaskService
import com.example.taskgateway.kafka.producer.KafkaTaskProducer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class TaskServiceTest {

    @Test
    fun `should produce valid tasks`() {
        val event = "dummy-event"

        val firstTask = mockk<Task>()
        val secondTask = mockk<Task>()
        val thirdTask = mockk<Task>()
        val forthTask = mockk<Task>()

        val firstElementTasks = listOf(firstTask, secondTask)

        val firstElement = mockk<TaskContract> {
            every { validate(event) } returns false
            every { generateTasks(event) } returns firstElementTasks
        }

        val secondElementTasks = listOf(thirdTask, forthTask)

        val secondElement = mockk<TaskContract> {
            every { validate(event) } returns true
            every { generateTasks(event) } returns secondElementTasks
        }

        val taskContracts = listOf(firstElement, secondElement)
        val kafkaTaskProducer = mockk<KafkaTaskProducer> {
            every { produce(thirdTask) } returns Unit
            every { produce(forthTask) } returns Unit
        }

        val taskService = TaskService(taskContracts, kafkaTaskProducer)

        taskService.process(event)

        verify(exactly = 0) { kafkaTaskProducer.produce(firstTask) }
        verify(exactly = 0) { kafkaTaskProducer.produce(secondTask) }
        verify(exactly = 1) { kafkaTaskProducer.produce(thirdTask) }
        verify(exactly = 1) { kafkaTaskProducer.produce(forthTask) }

    }

    @Test
    fun `should not produce any task`() {
        val event = "dummy-event"

        val firstTask = mockk<Task>()
        val secondTask = mockk<Task>()
        val thirdTask = mockk<Task>()
        val forthTask = mockk<Task>()

        val firstElementTasks = listOf(firstTask, secondTask)

        val firstElement = mockk<TaskContract> {
            every { validate(event) } returns false
            every { generateTasks(event) } returns firstElementTasks
        }

        val secondElementTasks = listOf(thirdTask, forthTask)

        val secondElement = mockk<TaskContract> {
            every { validate(event) } returns false
            every { generateTasks(event) } returns secondElementTasks
        }

        val taskContracts = listOf(firstElement, secondElement)
        val kafkaTaskProducer = mockk<KafkaTaskProducer> {
            every { produce(thirdTask) } returns Unit
            every { produce(forthTask) } returns Unit
        }

        val taskService = TaskService(taskContracts, kafkaTaskProducer)

        taskService.process(event)

        verify(exactly = 0) { kafkaTaskProducer.produce(firstTask) }
        verify(exactly = 0) { kafkaTaskProducer.produce(secondTask) }
        verify(exactly = 0) { kafkaTaskProducer.produce(thirdTask) }
        verify(exactly = 0) { kafkaTaskProducer.produce(forthTask) }

    }
}