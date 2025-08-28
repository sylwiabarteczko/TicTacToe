package syll25.tictactoe.web.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import syll25.tictactoe.web.repository.GameRepository;

import java.util.List;

@Component
public class AiMoveScheduler {
    private static final Logger log = LoggerFactory.getLogger(AiMoveScheduler.class);

    private final GameRepository gameRepository;
    private final GameService gameService;

    public AiMoveScheduler(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }
    @Scheduled(fixedDelayString = "${ai.scheduler.delay-ms:500}", initialDelay = 500)
    public void checkAndPlayAiMoves() {
        List<Long> ids = gameRepository.findIdsNeedingAiMove();
        if (ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            try {
                boolean moved = gameService.runAiTurnIfNeeded(id);
                if (moved) {
                    log.debug("AI moved in game {}", id);
                }
            } catch (Exception e) {
                log.warn("AI move failed for game {}", id, e);
            }
        }
    }


}
