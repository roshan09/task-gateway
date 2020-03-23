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
          "eventType": "ORDER_PLACED",
          "customerDetails" : {
            "customerId": "cust123",
            "name" : "John",
            "email" : "John@gmail.com",
            "mobileNumber" : "9988776655"
          },
          "orderDetails" : {
            "orderId" : "111",
            "orderedItem"  : "Sony Headphones"
          }
        }
        """.trimIndent()

        kafkaProducerTest.produce(incomingTopic, eventString)

        Thread.sleep(1000)

        val events = kafkaConsumerTest.findAllTasks()

        Assertions.assertEquals(2, events.size)

        val firstTask = events.first()
        val secondTask  = events.last()

        val expectedFirstTask = """{"taskType":"ORDER_PLACED_SMS_NOTIFICATION","payload":{"customerName":"John","orderedItem":"Sony Headphones","mobileNumber":"9988776655"}}"""
        val expectedSecondTask = """{"taskType":"ORDER_PLACED_EMAIL_NOTIFICATION","payload":{"customerName":"John","orderedItem":"Sony Headphones","email":"John@gmail.com"}}"""

        Assertions.assertEquals(firstTask, expectedFirstTask)
        Assertions.assertEquals(secondTask, expectedSecondTask)
    }
}
