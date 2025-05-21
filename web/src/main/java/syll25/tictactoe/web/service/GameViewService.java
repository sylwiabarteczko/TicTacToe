package syll25.tictactoe.web.service;

import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.state.StateDTO;

@Service
public class GameViewService {

    public String redirectToResult(StateDTO gameStateDTO, Long gameId) {
        if (gameStateDTO.isWinnerFound() || gameStateDTO.isDraw()) {
            return "gameResult";
        } else {
            return "redirect:/game/" + gameId;
        }
    }

    public char getColumnIndex(int colIndex) {
        return (char) ('A' + colIndex);
    }

}