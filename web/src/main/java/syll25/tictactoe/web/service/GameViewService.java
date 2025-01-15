package syll25.tictactoe.web.service;

import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.state.StateDTO;

@Service
public class GameViewService {

    public String redirectToResult(StateDTO stateDTO) {
        if (stateDTO.isWinnerFound() || stateDTO.isDraw()) {
            return "gameResult";
        } else {
            return "game";
        }
    }

    public char getColumnIndex(int colIndex) {
        return (char) ('A' + colIndex);
    }

}
