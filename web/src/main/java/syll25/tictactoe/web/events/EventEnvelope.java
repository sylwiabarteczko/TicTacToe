package syll25.tictactoe.web.events;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record EventEnvelope(
        UUID eventId,
        EventType eventType,
        Instant occurredAt,
        int schemaVersion,
        Map<String, Object> payload
) {
}
