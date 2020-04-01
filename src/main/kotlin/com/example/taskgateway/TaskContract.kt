package com.example.taskgateway

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.*
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jayway.jsonpath.JsonPath
import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import java.lang.Exception

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContract(
        val task: List<String>,
        val schema: Map<String, Any>,
        val targetEventMapping: List<PathResourceConfig>
) {

    fun validate(data: String): Boolean {
        val schema = SchemaLoader.load(JSONObject(schema))
        return schema.isValid(JSONObject(data))
    }

    fun generateTasks(data: String): List<Task> {

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

fun Schema.isValid(message: JSONObject): Boolean {
    return try {
        validate(message)
        true
    } catch (e: Exception) {
        false
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