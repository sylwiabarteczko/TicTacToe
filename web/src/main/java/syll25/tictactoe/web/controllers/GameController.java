package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/new")
    public String newGameForm() {
        return "newGame";
    }
    @PostMapping("/start")
    public String startNewGame(@RequestParam String player1Name,
                               @RequestParam String player2Name,
                               @RequestParam int boardSize,
                               Model model) {
        Board board = gameService.startNewGame(player1Name, player2Name, boardSize);
        model.addAttribute("board", board.toString()); // TODO co mamy w stringu? potrzebujemy state dto
        return "game";
    }
    @GetMapping("/{gameId}")
    public ModelAndView viewGame(@PathVariable Long gameId) {
        Game game = gameService.loadGame(gameId);
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("game", game);
        return modelAndView;
    }
    @PostMapping("/move")
    public ModelAndView makeMove(
            @RequestParam("gameId") Long gameId,
            @RequestParam("row") int row,
            @RequestParam("col") int col) {

        Board board = gameService.makeMove(gameId, row, col);
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("board", board.toString()); // TODO co mamy w stringu? potrzebujemy state dto
        return modelAndView;
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
