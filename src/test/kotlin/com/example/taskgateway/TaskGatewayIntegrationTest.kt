package com.example.taskgateway

import com.example.taskgateway.kafkaSetup.EmbeddedKafka
import com.example.taskgateway.kafkaSetup.KafkaConsumerTest
import com.example.taskgateway.kafkaSetup.KafkaProducerTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(EmbeddedKafka::class)
class TaskGatewayIntegrationTest {

    @Autowired
    lateinit var kafkaConsumerTest : KafkaConsumerTest

    @Autowired
    lateinit var kafkaProducerTest : KafkaProducerTest

    @Value("\${kafka.consumer.topic}")
    lateinit var incomingTopic : String

    @BeforeEach
    fun setUp() {
        kafkaConsumerTest.clear()
    }

    @Test
    fun `should consume event and produce dependent task`() {

        val eventString = """
        {
            "eventType" : "applicationEvent",
            "applicationId" : "app1",
            "userId" : "user1",
            "name" : "name1"
        }
        """.trimIndent()

        kafkaProducerTest.produce(incomingTopic, eventString)

        Thread.sleep(1000)

        val events = kafkaConsumerTest.findAllTasks()

        Assertions.assertEquals(2, events.size)

        val firstTask = events.first()
        val secondTask  = events.last()

        val expectedFirstTask = """{"taskType":"first-task","payload":{"applicationId":"app1","customerId":"user1","name":1}}"""
        val expectedSecondTask = """{"taskType":"second-task","payload":{"applicationId":"app1","customerId":"user1","name":1}}"""

        Assertions.assertEquals(firstTask, expectedFirstTask)
        Assertions.assertEquals(secondTask, expectedSecondTask)
    }
}
