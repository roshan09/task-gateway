package com.example.taskgateway.kafkaSetup

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.kafka.test.EmbeddedKafkaBroker

class EmbeddedKafka : AfterAllCallback, BeforeAllCallback {
    private val embeddedKafkaBroker = EmbeddedKafkaBroker(1, true)

    override fun afterAll(context: ExtensionContext?) {
        embeddedKafkaBroker.destroy()
    }

    override fun beforeAll(context: ExtensionContext?) {
        embeddedKafkaBroker.afterPropertiesSet()
    }

}