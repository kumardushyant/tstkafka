package tut.dushyant.kafka.web.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class KafkaWebDTOSerializer(private val objectMapper: ObjectMapper): Serializer<String>  {

    constructor(): this(ObjectMapper())

    override fun serialize(topic: String?, data: String?): ByteArray? {
        objectMapper.readValue<KafkaWebDTO>(data, KafkaWebDTO::class.java).let {
            return objectMapper.writeValueAsBytes(it)
        }
    }

}