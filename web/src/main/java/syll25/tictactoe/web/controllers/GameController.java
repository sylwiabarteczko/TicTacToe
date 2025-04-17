package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.model.GameStateDTO;
import syll25.tictactoe.web.service.GameService;
import syll25.tictactoe.web.service.GameViewService;

import java.util.List;
import java.security.Principal;

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
    public String startNewGame(@RequestParam int boardSize,
                               Principal principal) {
        String player1Name = principal.getName();

        Long gameId = gameService.startNewGame(player1Name, boardSize);
        return "redirect:/game/" + gameId;
    }

    @GetMapping("/{gameId}")
    public String viewGame(@PathVariable Long gameId,
                           Principal principal,
                           Model model) {
        Game game = gameService.getGameById(gameId);
        String username = principal.getName();

        if (!username.equals(game.getPlayer1Name())) {
            if (game.getPlayer2Name().equals("Player")) {
                game.setPlayer2Name(username);
                if (game.getCurrentPlayer() == null) {
                    game.setCurrentPlayer(game.getPlayer1Name());
                }
                gameService.save(game);
            } else if (!username.equals(game.getPlayer2Name())) {
                model.addAttribute("error", "You are not a player in this game.");
                return "error";
            }
        }

        GameStateDTO gameStateDTO = gameService.loadGame(gameId);
        model.addAttribute("isPlayer1", username.equals(game.getPlayer1Name()));
        model.addAttribute("isPlayer2", username.equals(game.getPlayer2Name()));
        model.addAttribute("gameStateDTO", gameStateDTO);
        model.addAttribute("gameId", gameId);

        return "game";
    }

    @PostMapping("/move")
    @ResponseBody
    public GameStateDTO makeMove(
            @RequestParam("gameId") Long gameId,
            @RequestParam("row") int row,
            @RequestParam("col") int col,
            Principal principal) {

        Game game = gameService.getGameById(gameId);
        String username = principal.getName();

        if(game.getCurrentPlayer() == null) {
            game.setCurrentPlayer(game.getPlayer1Name());
            gameService.save(game);
        }

        if (!username.equals(game.getCurrentPlayer())) {
            throw new IllegalStateException("Not your turn");
        }

        StateDTO updatedState = gameService.makeMove(gameId, row, col, username);
        return new GameStateDTO(updatedState, gameId);
    }
    @GetMapping("/{gameId}/state")
    public ResponseEntity<GameStateDTO> getGameState(@PathVariable Long gameId) {
        Game game = gameService.loadGameEntity(gameId);
        GameStateDTO gameStateDTO = gameService.convertToGameStateDTO(game);
        return ResponseEntity.ok(gameStateDTO);
    }

    @GetMapping("/gameResult/{playerName}/{gameId}")
    public String gameResult(@PathVariable String playerName,
                             @PathVariable Long gameId, Model model) {
        GameStateDTO gameStateDTO = gameService.loadGame(gameId);
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

