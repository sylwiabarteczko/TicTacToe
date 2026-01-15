package syll25.tictactoe.web.kafka;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import syll25.tictactoe.web.events.EventEnvelope;
import syll25.tictactoe.web.events.EventType;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final KafkaProducer producer;

    private final EventPublisher publisher;

    public MessageController(KafkaProducer producer,
                             EventPublisher publisher) {
        this.producer = producer;
        this.publisher = publisher;
    }
    @PostMapping("/send")
    public String send(@RequestParam String msg) {
        producer.send("test-topic", msg);
        return "sent: " + msg;
    }
    @PostMapping("/dev/user-registered")
    public String userRegistered(@RequestParam String username) {
        EventEnvelope event = new EventEnvelope(
                UUID.randomUUID(),
                EventType.USER_REGISTERED,
                Instant.now(),
                1,
                Map.of("username", username)
        );
        publisher.publish(username, event);
        return "sent USER_REGISTERED for " + username;
    }

}
