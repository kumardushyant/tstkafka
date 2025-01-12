package tut.dushyant.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import tut.dushyant.kafka.web.dto.KafkaWebDTO

@Service
class KafkaProducer(
    @Qualifier("testKafkaTemplate") private val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${kafka.topic:test}") private val topic: String,
    private val mapper: ObjectMapper
) {
    fun send(kafkaWebDTO: KafkaWebDTO) : String {
        kafkaTemplate.send(topic, kafkaWebDTO.hashCode().toString(), mapper.writeValueAsString(kafkaWebDTO))
        return "Message sent to topic: $topic"
    }
}