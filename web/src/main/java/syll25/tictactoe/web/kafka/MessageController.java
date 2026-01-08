package syll25.tictactoe.web.kafka;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final KafkaProducer producer;

    public MessageController(KafkaProducer producer) {
        this.producer = producer;
    }
    @PostMapping("/send")
    public String send(@RequestParam String msg) {
        producer.send("test-topic", msg);
        return "sent: " + msg;
    }

}
