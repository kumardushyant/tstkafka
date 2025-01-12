package tut.dushyant.kafka.web.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull

data class KafkaWebDTO @JsonCreator constructor (
    @JsonProperty("message") @NotNull val message: String,
    @JsonProperty("status") @NotNull val status: Int
)