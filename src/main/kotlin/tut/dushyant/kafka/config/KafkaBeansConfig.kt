package tut.dushyant.kafka.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import tut.dushyant.kafka.consumer.KafkaConsumer
import tut.dushyant.kafka.web.dto.KafkaWebDTO

@Configuration
@EnableKafka
class KafkaBeansConfig (
    val kafkaValues: KafkaValues
) {

    @Bean("testKafAdmin")
    fun kafkaAdmin(): KafkaAdmin {
        return KafkaAdmin(mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaValues.bootstrapServers,
            AdminClientConfig.SECURITY_PROTOCOL_CONFIG to kafkaValues.properties.securityProtocol,
            "sasl.jaas.config" to kafkaValues.properties.sasl.jaas.config,
            "sasl.mechanism" to kafkaValues.properties.sasl.mechanism
        ))
    }

    @Bean
    fun kafkaTopic(): NewTopic {
        return TopicBuilder.name(kafkaValues.topic)
            .partitions(2)
            .replicas(3)
            .build()
    }

    @Bean("testKafkaConsumer")
    fun kafkaProducerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory<String, String>(mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaValues.bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringSerializer",
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to "tut.dushyant.kafka.web.dto.KafkaWebDTOSerializer",
            "security.protocol" to kafkaValues.properties.securityProtocol,
            "sasl.jaas.config" to kafkaValues.properties.sasl.jaas.config,
            "sasl.mechanism" to kafkaValues.properties.sasl.mechanism
        ))
    }

    @Bean("testKafkaTemplate")
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate<String, String>(kafkaProducerFactory())
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaWebDTO>> {
        return ConcurrentKafkaListenerContainerFactory<String, KafkaWebDTO>().apply {
            consumerFactory = DefaultKafkaConsumerFactory(mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaValues.bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to kafkaValues.groupId,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringDeserializer",
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to "tut.dushyant.kafka.web.dto.KafkaWebDTODeserializer",
                "security.protocol" to kafkaValues.properties.securityProtocol,
                "sasl.jaas.config" to kafkaValues.properties.sasl.jaas.config,
                "sasl.mechanism" to kafkaValues.properties.sasl.mechanism
            ))
            setConcurrency(2)
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        }
    }
}