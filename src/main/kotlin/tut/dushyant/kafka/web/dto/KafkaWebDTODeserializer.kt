package tut.dushyant.kafka.web.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer

class KafkaWebDTODeserializer(private val objectMapper: ObjectMapper): Deserializer<KafkaWebDTO> {

    constructor(): this(ObjectMapper())

    override fun deserialize(topic: String?, data: ByteArray?): KafkaWebDTO? {
        return data?.let { objectMapper.readValue(it, KafkaWebDTO::class.java) }
    }
}