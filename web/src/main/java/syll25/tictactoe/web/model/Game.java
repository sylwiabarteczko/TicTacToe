package syll25.tictactoe.web.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "board_state", columnDefinition = "TEXT")
    private final String boardState;

    @Column(name = "player1_name")
    private final String player1Name;

    @Column(name = "player2_name")
    private final String player2Name;

    @Column(name = "current_player")
    private final String currentPlayer;

    @Column(name = "game_over")
    private final boolean gameOver;

    protected Game() {
        this.id = null;
        this.boardState = null;
        this.player1Name = null;
        this.player2Name = null;
        this.currentPlayer = null;
        this.gameOver = false;
    }

    public Game(String boardState, String player1Name, String player2Name, String currentPlayer, boolean gameOver) {
        this.id = null;
        this.boardState = boardState;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
    }
}
