package com.example.taskgateway

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.*
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContract(
        val sourceEventType: String,
        val task: List<String>,
        val schema: Map<String, Any>,
        val targetEventMapping: List<PathResourceConfig>
)


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(value = [
    Type(value = JsonPathResourceConfig::class, name = "jsonResource"),
    Type(value = DummyPathResourceConfig::class, name = "dummyResource")
])
interface PathResourceConfig {
    fun findValue(): Any
}

class JsonPathResourceConfig : PathResourceConfig {
    lateinit var type: String
    lateinit var key: String
    lateinit var mappedKeyPath: String

    override fun findValue(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class DummyPathResourceConfig : PathResourceConfig {
    lateinit var type: String
    lateinit var key: String
    lateinit var mappedKeyPath: String

    override fun findValue(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

