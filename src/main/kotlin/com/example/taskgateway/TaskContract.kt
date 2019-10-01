package com.example.taskgateway

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.*
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jayway.jsonpath.JsonPath

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContract(
        val sourceEventType: String,
        val task: List<String>,
        val schema: Map<String, Any>,
        val targetEventMapping: List<PathResourceConfig>
) {

    private fun validate(data: String): Boolean {
        val dataMap = jacksonObjectMapper().readValue(data, Map::class.java) as Map<String, Any>
        return dataMap["eventType"] as String == sourceEventType
    }

    fun generateTasks(data: String): List<Task> {

        if (!validate(data)) return emptyList()

        val payload = mutableMapOf<String, Any>()

        targetEventMapping.map { resource ->
            when (resource) {
                is JsonPathResourceConfig -> {
                    payload.put(resource.key, resource.findValue(data))
                }
                is DummyPathResourceConfig -> {
                    payload.put(resource.key, resource.findValue(data))
                }
                else -> {
                    println("Invalid Resource config")
                }
            }
        }

        return task.map { Task(taskType = it, payload = payload) }
    }
}

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(value = [
    Type(value = JsonPathResourceConfig::class, name = "jsonResource"),
    Type(value = DummyPathResourceConfig::class, name = "dummyResource")
])
interface PathResourceConfig {
    fun findValue(data: String): Any
}

class JsonPathResourceConfig : PathResourceConfig {
    lateinit var type: String
    lateinit var key: String
    lateinit var mappedKeyPath: String

    override fun findValue(data: String): Any {
        return JsonPath.read(data, mappedKeyPath)
    }
}

class DummyPathResourceConfig : PathResourceConfig {
    lateinit var type: String
    lateinit var key: String
    lateinit var mappedKeyPath: String

    override fun findValue(data: String): Any {
        // some other operation
        // calculate the result
        return 1
    }
}


data class Task(val taskType: String, val payload: Map<String, Any>)