package tut.dushyant.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import tut.dushyant.kafka.config.KafkaValues

@SpringBootApplication
@EnableConfigurationProperties(KafkaValues::class)
open class TstkafkaApplication

fun main(args: Array<String>) {
	runApplication<TstkafkaApplication>(*args)
}

