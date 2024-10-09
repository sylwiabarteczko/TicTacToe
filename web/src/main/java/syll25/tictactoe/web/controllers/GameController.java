package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        Game game = gameService.startNewGame();
        model.addAttribute("board", game.getBoardState());
        model.addAttribute("game", game);
        return "game";
    }

    @PostMapping("/playerMove")
    public String playerMove(@RequestParam Long gameId, @RequestParam int row, @RequestParam int col, Model model) {
        Game game = gameService.makeMove(gameId, row, col);
        model.addAttribute("board", game.getBoardState());
        model.addAttribute("game", game);
        return "game";
    }
    @PostMapping("/load")
    public String loadGame(@RequestParam Long gameId, Model model) {
        Game game = gameService.loadGame(gameId);
        model.addAttribute("board", game.getBoardState());
        model.addAttribute("game", game);
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
