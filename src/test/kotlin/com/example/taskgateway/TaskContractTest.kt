package com.example.taskgateway

import kafka.utils.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TaskContractTest {

    @Test
    fun `should return true if event is valid for given contract`() {
        val jsonSchema = mapOf("\$schema" to "http://json-schema.org/draft-07/schema#",
                "type" to "object",
                "required" to listOf(
                        "eventType",
                        "eventId"
                ))
        val data = """{ "eventType" : "eventType2", "eventId" : "eventType2" }"""
        val taskContract = TaskContract(emptyList(), jsonSchema, emptyList())
        val result = taskContract.validate(data)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return false if event is not valid for given contract`() {
        val jsonSchema = mapOf("\$schema" to "http://json-schema.org/draft-07/schema#",
                "type" to "object",
                "required" to listOf(
                        "eventType",
                        "eventId"
                ))
        val data = """{ "eventType" : "eventType2" }"""
        val taskContract = TaskContract(emptyList(), jsonSchema, emptyList())
        val result = taskContract.validate(data)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should generate task when task contract has jsonPathResource`() {

        val firstJsonConfig = with(JsonPathResourceConfig()) {
            type = ""
            key = "key1"
            mappedKeyPath = "$.sourceKey1"
            this
        }

        val secondJsonConfig = with(JsonPathResourceConfig()) {
            type = ""
            key = "key2"
            mappedKeyPath = "$.sourceKey2"
            this
        }

        val taskContract = TaskContract(listOf("task1", "task2"),
                emptyMap(), listOf(firstJsonConfig, secondJsonConfig))

        val data = """
            {
                "sourceKey1" : "value1",
                "sourceKey2" : "value2",
                "sourceKey3" : "value3",
            }
        """.trimIndent()

        val expectedTaskPayload = mapOf("key1" to "value1", "key2" to "value2")

        val tasks = taskContract.generateTasks(data)

        val expectedTask1 = Task("task1", expectedTaskPayload)
        val expectedTask2 = Task("task2", expectedTaskPayload)

        Assertions.assertEquals(listOf(expectedTask1, expectedTask2), tasks)
    }
}