package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
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

    @GetMapping("/new")
    public String newGameForm() {
        return "newGame";
    }
    @PostMapping("/start")
    public String startNewGame(@RequestParam String player1Name,
                               @RequestParam String player2Name,
                               @RequestParam int boardSize) {
        Long Id = gameService.startNewGame(player1Name, player2Name, boardSize);
        return "redirect:/game/" + Id;
    }

    @GetMapping("/{gameId}")
    public String viewGame(@PathVariable Long gameId, Model model) {
        StateDTO stateDTO = gameService.loadGame(gameId);
        model.addAttribute("stateDTO", stateDTO);
        return "game";
    }

    @PostMapping("/move")
    public String makeMove(
            @RequestParam("gameId") Long gameId,
            @RequestParam("row") int row,
            @RequestParam("col") int col,
            Model model) {

        StateDTO stateDTO = gameService.makeMove(gameId, row, col);

        model.addAttribute("stateDTO", stateDTO);

        return gameViewService.redirectToResult(stateDTO);
    }

    @PostMapping("/load")
    public String loadGame(@RequestParam Long gameId, Model model) {
        StateDTO stateDTO = gameService.loadGame(gameId);

        model.addAttribute("board", stateDTO);
        return "game";
    }

    // TODO na potem :D  - lista aktywnych gier
    @GetMapping("/list-games")
    public String listGames(Model model) {
        List<Game> games = gameService.listGames();
        model.addAttribute("games", games);
        return "games";
    }


}
