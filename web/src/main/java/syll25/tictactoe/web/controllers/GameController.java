package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.service.GameService;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/start")
    public String startNewGame(Model model) {
        Board board = gameService.startNewGame();
        model.addAttribute("board", board);
        return "game";
    }

    @PostMapping("/playerMove")
    public String playerMove(@RequestParam int row, @RequestParam int col, /*TODO long gameId, */Model model) {
        Board board = gameService.makeMove(row, col); // TODO id
        model.addAttribute("board", board);
        return "game";
    }
        @PostMapping("/load")
        public String loadGame(@RequestParam String filename, Model model) { // mamy bazę wiec po co nam pliki? co będzie identyfikatorem zamiast filename?
            Board board = gameService.loadGame(filename);
            model.addAttribute("board", board);
            return "game";
        }

    @GetMapping("/list-games")
    public String listGames(Model model) {
        List<Game> games = gameService.listGames();
        model.addAttribute("games", games);
        return "games";
    }


}
