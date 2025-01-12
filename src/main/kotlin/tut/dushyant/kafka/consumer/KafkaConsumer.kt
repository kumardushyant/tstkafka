package tut.dushyant.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import tut.dushyant.kafka.config.KafkaValues
import tut.dushyant.kafka.web.dto.KafkaWebDTO

@Service
class KafkaConsumer(
    val kafkaValues: KafkaValues
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    @KafkaListener(
        id="testApp1",
        groupId = "\${kafka.group-id}", topicPartitions = [
            TopicPartition(topic = "\${kafka.topic}", partitions = ["0"])
        ]
    )
    fun listen(record: ConsumerRecord<String, KafkaWebDTO>, acknowledgment: Acknowledgment) {
        logger.info("Received message: ${record.value()} from partition 0")
        acknowledgment.acknowledge()
    }

    @KafkaListener(
        id="testApp2",
        groupId = "\${kafka.group-id}", topicPartitions = [
            TopicPartition(topic = "\${kafka.topic}", partitions = ["1"])
        ]
    )
    fun listen1(record: ConsumerRecord<String, KafkaWebDTO>, acknowledgment: Acknowledgment) {
        logger.info("Received message: ${record.value()} from partition 1")
        acknowledgment.acknowledge()
    }
}