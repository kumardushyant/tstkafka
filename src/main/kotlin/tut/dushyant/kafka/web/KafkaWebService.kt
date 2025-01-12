package tut.dushyant.kafka.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tut.dushyant.kafka.producer.KafkaProducer
import tut.dushyant.kafka.web.dto.KafkaWebDTO

@RestController
@RequestMapping("/kafka")
class KafkaWebService(private val kafkaProducer: KafkaProducer) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaWebService::class.java)
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody kafkaWebDTO: KafkaWebDTO) {
        LOGGER.atInfo().log(kafkaProducer.send(kafkaWebDTO))
    }
}