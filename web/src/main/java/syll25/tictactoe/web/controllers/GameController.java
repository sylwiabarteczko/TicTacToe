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

import java.nio.file.AccessDeniedException;
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
                           Model model)
            throws AccessDeniedException {
        Game game = gameService.getGameById(gameId);
        String username = principal.getName();

        if(!username.equals(game.getPlayer1Name()) && !username.equals(game.getPlayer2Name())) {
            throw new AccessDeniedException("You are not allow to access this game. ");
        }

        if (game.getPlayer2Name() == null && !username.equals(game.getPlayer1Name())) {
            game.setPlayer2Name(username);
            gameService.save(game);
        }
        GameStateDTO gameStateDTO = gameService.loadGame(gameId);

        boolean isPlayer1 = username.equals(game.getPlayer1Name());
        boolean isPlayer2 = username.equals(game.getPlayer2Name());

        model.addAttribute("isPlayer1", isPlayer1);
        model.addAttribute("isPlayer2", isPlayer2);
        model.addAttribute("gameStateDTO", gameStateDTO);
        return "game";
    }


    @PostMapping("/move")
    public String makeMove(
            @RequestParam("gameId") Long gameId,
            @RequestParam("row") int row,
            @RequestParam("col") int col,
            Principal principal,
            Model model) {

        Game game = gameService.getGameById(gameId);
        String currentPlayer = game.getCurrentPlayer();

        if (!principal.getName().equals(currentPlayer)) {
            model.addAttribute("error", "Nie twoja kolej!");
            model.addAttribute("gameStateDTO", gameService.loadGame(gameId));
            return "notYourTurn";
        }

        try {
            StateDTO updatedState = gameService.makeMove(gameId, row, col);
            model.addAttribute("gameStateDTO", new GameStateDTO(updatedState, gameId));
            model.addAttribute("gameId", gameId);

            if (updatedState.isWinnerFound()) {
                return "redirect:/game/gameResult/" + updatedState.getCurrentPlayer() + "/" + gameId;
            }
            return "redirect:/game/" + gameId;

        } catch (IllegalStateException e) {
            model.addAttribute("error", "Wrong move");
            return "error";
        }
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
