package tut.dushyant.kafka.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kafka")
data class KafkaValues (
    var bootstrapServers: String,
    var topic: String,
    var groupId: String,
    var properties: KafkaPropertiesSasl
)

data class KafkaPropertiesSasl(
    var securityProtocol: String,
    var sasl: KafkaSasl
)

data class KafkaSasl(
    var jaas: KafkaJaas,
    var mechanism: String
)

data class KafkaJaas(
    var config: String
)