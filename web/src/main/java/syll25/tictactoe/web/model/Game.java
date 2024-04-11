package syll25.tictactoe.web.model;

import jakarta.persistence.*;
import syll25.tictactoe.logic.state.StateDTO;

import java.time.ZonedDateTime;

@Table(name = "game_states")
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "board_state", columnDefinition = "TEXT")
    private String boardState;

    @Column(name = "player1_name")
    private String player1Name;

    @Column(name = "player2_name")
    private String player2Name;

    @Column(name = "player1_login")
    private String player1Login;

    @Column(name = "player2_login")
    private String player2Login;

    @Column(name = "current_player", nullable = false)
    private String currentPlayer;

    @Column(name = "game_over", nullable = false)
    private boolean gameOver;

    @Column
    private Character player1Symbol;

    @Column
    private Character player2Symbol;

    @Column
    private ZonedDateTime createdDate;

    public Game() {

    }

    public Game(String boardState, String player1Name, Character player1Symbol, String player2Name,
                Character player2Symbol, String player1Login, String player2Login, String currentPlayer, boolean gameOver) {
        this.boardState = boardState;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Login = player1Login;
        this.player2Login = player2Login;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.player1Symbol = player1Symbol;
        this.player2Symbol = player2Symbol;
        this.createdDate = ZonedDateTime.now();

    }

    public Game(long id, String player1, String player2, int size) {
    }

    public Game(String s, String player1, char x, String player2, char o, boolean b) {
    }

    public Game(String boardStateJson, String name, char symbol, String name1, char symbol1, String player1Login, String name2, boolean b) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardState() {
        return this.boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", boardState='" + boardState + '\'' +
                ", player1Name='" + player1Name + '\'' +
                ", player2Name='" + player2Name + '\'' +
                ", currentPlayer='" + currentPlayer + '\'' +
                ", gameOver=" + gameOver +
                '}';

    }


    public Character getPlayer1Symbol() {
        return player1Symbol;
    }


    public Character getPlayer2Symbol() {
        return player2Symbol;
    }

    public void setPlayer1Symbol(Character player1Symbol) {
        this.player1Symbol = player1Symbol;
    }

    public void setPlayer2Symbol(Character player2Symbol) {
        this.player2Symbol = player2Symbol;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void updateFromStateDTO(StateDTO stateDTO) {
        this.currentPlayer = stateDTO.getCurrentPlayer();
        this.gameOver = stateDTO.isGameOver();
    }

    public String getPlayer1Login() {
        return player1Login;
    }

    public void setPlayer1Login(String player1Login) {
        this.player1Login = player1Login;
    }

    public String getPlayer2Login() {
        return player2Login;
    }

    public void setPlayer2Login(String player2Login) {
        this.player2Login = player2Login;
    }
}

