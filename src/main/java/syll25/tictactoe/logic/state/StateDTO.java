package syll25.tictactoe.logic.state;

public class StateDTO {
    public String player1Name;
    public String player2Name;
    public String player1Sign;
    public String player2Sign;
    public String[][] board;
    public int size;

} //TODO zapoznac sie i przerobic na record. W StateDTO dwa obiekty klasy PlayerDTO (obiekt Player1 i Player2)
// TODO size - metoda ktora bedzie wyliczac rozmiar na podstawie board