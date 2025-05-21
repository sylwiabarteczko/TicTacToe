package syll25.tictactoe.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.repository.GameRepository;
import org.flywaydb.core.Flyway;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryTest {

    @Container
    static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        flyway.migrate();
        gameRepository.deleteAll();
    }

    @Test
    void saveAndFindGameById() {

        Game game = new Game();
        game.setPlayer1Name("Sylwia");
        game.setPlayer2Name("Sabina");
        game.setCurrentPlayer("Sylwia");
        game.setPlayer1Symbol('X');
        game.setPlayer2Symbol('O');
        game.setBoardState("XOX,O,O");

        Game savedGame = gameRepository.save(game);
        Optional<Game> foundGame = gameRepository.findById(savedGame.getId());

        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().getId()).isEqualTo(savedGame.getId());
        assertThat(foundGame.get().getPlayer1Name()).isEqualTo("Sylwia");
        assertThat(foundGame.get().getPlayer2Name()).isEqualTo("Sabina");
        assertThat(foundGame.get().getCurrentPlayer()).isEqualTo("Sylwia");
        assertThat(foundGame.get().getBoardState()).isEqualTo("XOX,O,O");
    }
}
