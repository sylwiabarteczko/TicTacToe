package syll25.tictactoe.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import syll25.tictactoe.web.service.GameViewService;

@Configuration
public class GameConfiguration {

    @Bean
    public GameViewService gameViewService() {
        return new GameViewService();
    }
}
