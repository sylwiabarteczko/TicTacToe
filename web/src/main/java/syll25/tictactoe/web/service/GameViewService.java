package syll25.tictactoe.web.service;

import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.state.StateDTO;

@Service
public class GameViewService {

    public String redirectToResult(StateDTO stateDTO) {
        if (stateDTO.isWinnerFound()) {
            return "winner";
        } else if (stateDTO.isDraw()) {
            return "draw";
        } else {
            return "game";
        }
    }

    public char getColumnIndex(int colIndex) {
        return (char) ('A' + colIndex);
    }

}
