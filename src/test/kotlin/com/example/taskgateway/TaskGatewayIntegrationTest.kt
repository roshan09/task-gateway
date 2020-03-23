package com.example.taskgateway

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(EmbeddedKafka::class)
class TaskGatewayIntegrationTest {

    @Test
    fun name() {

    }
}


class EmbeddedKafka : AfterAllCallback, BeforeAllCallback {
    private val embeddedKafkaBroker = EmbeddedKafkaBroker(1, true)

    override fun afterAll(context: ExtensionContext?) {
        embeddedKafkaBroker.destroy()
    }

    override fun beforeAll(context: ExtensionContext?) {
        embeddedKafkaBroker.afterPropertiesSet()
    }

}