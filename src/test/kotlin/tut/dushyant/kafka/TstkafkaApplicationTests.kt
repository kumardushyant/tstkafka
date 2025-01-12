package tut.dushyant.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.InterruptException
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import tut.dushyant.kafka.web.dto.KafkaWebDTO

@SpringBootTest
class TstkafkaApplicationTests {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TstkafkaApplicationTests::class.java)
    }

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun contextLoads() {
    }

    @Test
    @Throws(InterruptException::class)
    fun testKafka() {
        for (i in 1..10) {
            if (i % 2 == 0)
                sendMsg(0, i)
            else
                sendMsg(1, i)
        }
    }

    fun sendMsg(partition: Int, i: Int) {
        kafkaTemplate.send("test", partition, "testKey", objectMapper.writeValueAsString(KafkaWebDTO(status = i, message = "Message $i")) ).thenAccept { sendResult ->
            logger.atInfo()
                .log("Sent message: ${sendResult.producerRecord.key()}: ${sendResult.producerRecord.value()} on topic ${sendResult.recordMetadata.topic()} at partition $partition")
        }.exceptionally { throwable ->
            logger.atError()
                .log("Error sending message: ${throwable.message}")
            null
        }
    }

}