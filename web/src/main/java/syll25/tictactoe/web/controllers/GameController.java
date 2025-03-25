package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.model.GameStateDTO;
import syll25.tictactoe.web.service.GameService;
import syll25.tictactoe.web.service.GameViewService;
import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final GameViewService gameViewService;

    @Autowired
    public GameController(GameService gameService, GameViewService gameViewService) {
        this.gameService = gameService;
        this.gameViewService = gameViewService;
    }

    @GetMapping("/list-games")
    public String listGames(Model model) {
        List<Game> activeGames = gameService.listActiveGames();
        model.addAttribute("games", activeGames);
        return "gameList";
    }

    @GetMapping("/new")
    public String newGameForm() {
        return "newGame";
    }

    @PostMapping("/start")
    public String startNewGame(@RequestParam String player1Name,
                               @RequestParam String player2Name,
                               @RequestParam int boardSize) {
        Long gameId = gameService.startNewGame(player1Name, player2Name, boardSize);
        return "redirect:/game/" + gameId;
    }

    @GetMapping("/{gameId}")
    public String viewGame(@PathVariable Long gameId, Model model) {
        GameStateDTO gameStateDTO = gameService.loadGame(gameId);

        model.addAttribute("gameStateDTO", gameStateDTO);
        return "game";
    }

    @PostMapping("/move")
    public String makeMove(
            @RequestParam("gameId") Long gameId,
            @RequestParam("row") int row,
            @RequestParam("col") int col,
            Model model) {

        StateDTO gameStateDTO = gameService.makeMove(gameId, row, col);
        model.addAttribute("gameStateDTO", gameStateDTO);
        model.addAttribute("gameId", gameId);

        if (gameStateDTO.isWinnerFound()) {
            return "redirect:/game/gameResult/" + gameStateDTO.getCurrentPlayer() + "/" + gameId;
        }

        return gameViewService.redirectToResult(gameStateDTO, gameId);
    }

    @GetMapping("/gameResult/{playerName}/{gameId}")
    public String gameResult(
            @PathVariable String playerName,
            @PathVariable Long gameId,
            Model model) {

        GameStateDTO gameStateDTO = gameService.loadGame(gameId);

        if (gameStateDTO.isWinnerFound()) {
            gameStateDTO.setCurrentPlayer(playerName);
        }

        model.addAttribute("gameStateDTO", gameStateDTO);
        return "gameResult";
    }

    @PostMapping("/load")
    public String loadGame(@RequestParam Long gameId, Model model) {
        GameStateDTO gameStateDTO = gameService.loadGame(gameId);

        model.addAttribute("gameStateDTO", gameStateDTO);
        return "game";
    }

}
