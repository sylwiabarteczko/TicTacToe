package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.model.GameStateDTO;
import syll25.tictactoe.web.model.MoveResponseDTO;
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
    public String newGameForm(@RequestParam(name = "mode", required = false) String mode, Model model) {
        if (mode == null) {
            return "choosePlayer";
        }
        model.addAttribute("mode", mode);
        return "newGame";
    }

    @PostMapping("/start")
    public String startNewGame(@RequestParam String player1Name,
                               @RequestParam String player2Name,
                               @RequestParam int boardSize,
                               @RequestParam String mode,
                               Principal principal) {

        Long gameId = gameService.startNewGame(player1Name, player2Name, boardSize, principal.getName(), mode);
        return "redirect:/game/" + gameId;
    }

    @GetMapping("/{gameId}")
    public String viewGame(@PathVariable Long gameId,
                           Principal principal,
                           Model model) {
        Game game = gameService.getGameById(gameId);
        String login = principal.getName();

        boolean presentPlayer = gameService.assignPlayer(game, login);
        if (!presentPlayer) {
            model.addAttribute("error", "You are not a player in this game.");
            return "error";
        }

        boolean isPlayer1 = login.equals(game.getPlayer1Login());
        boolean isPlayer2 = login.equals(game.getPlayer2Login());

        GameStateDTO gameStateDTO = gameService.loadGame(gameId);

        model.addAttribute("gameStateDTO", gameStateDTO);
        model.addAttribute("isPlayer1", isPlayer1);
        model.addAttribute("isPlayer2", isPlayer2);
        model.addAttribute("player1Name", game.getPlayer1Name());
        model.addAttribute("player2Name", game.getPlayer2Name());
        model.addAttribute("gameId", gameId);

        return "game";
    }

    @PostMapping("/move")
    @ResponseBody
    public MoveResponseDTO makeMove(
            @RequestParam("gameId") Long gameId,
            @RequestParam("row") int row,
            @RequestParam("col") int col,
            Principal principal) {

        Game game = gameService.getGameById(gameId);
        String login = principal.getName();

        String playerName = login.equals(game.getPlayer1Login())
                ? game.getPlayer1Name()
                : game.getPlayer2Name();

        if (!playerName.equals(game.getCurrentPlayer())) {
            throw new IllegalStateException("Not your turn");
        }

        StateDTO updatedState = gameService.makeMove(gameId, row, col, playerName);
        boolean yourTurn = updatedState.getCurrentPlayer().equals(playerName);
        return new MoveResponseDTO(updatedState, yourTurn);
    }

    @GetMapping("/{gameId}/state")
    @ResponseBody
    public MoveResponseDTO getGameState(@PathVariable Long gameId, Principal principal) {
        Game game = gameService.loadGameEntity(gameId);
        StateDTO stateDTO = gameService.convertToStateDTO(game);

        String login = principal.getName();
        String myNick = login.equals(game.getPlayer1Login())
                ? game.getPlayer1Name()
                : game.getPlayer2Name();

        boolean yourTurn = stateDTO.getCurrentPlayer().equals(myNick);
        return new MoveResponseDTO(stateDTO, yourTurn);
    }

    @PostMapping("/load")
    public String loadGame(@RequestParam Long gameId, Model model) {
        GameStateDTO gameStateDTO = gameService.loadGame(gameId);

        model.addAttribute("gameStateDTO", gameStateDTO);
        return "game";
    }

    @GetMapping("/api/game/{id}/best-move")
    public ResponseEntity<MoveResponseDTO> getBestMove(@PathVariable Long id, Principal principal) {
        MoveResponseDTO move = gameService.getBestMoveForAI(id, principal.getName());
        return ResponseEntity.ok(move);
    }
}
