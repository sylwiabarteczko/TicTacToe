package syll25.tictactoe.logic.state;
import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;
public class TxtState implements State{
    @Override
    public void save(GameBoard board, Player player1, Player player2) {

    }

    @Override
    public StateDTO load(String filename) {  //DTO Data Transfer Object
        // TODO
        String fileContent = ;// odczyt plikuu
        StateDTO stateDTO = new StateDTO();
        stateDTO.player1Name = filename.split(";")// .. coś tam wczytane z pliku
        stateDTO.player2Name = // .. coś tam wczytane z pliku
        // .. itd
        return stateDTO;
    }
}
