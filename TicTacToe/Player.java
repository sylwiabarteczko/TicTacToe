import java.util.Random;

public class Player {

    public Player() {

        char[] symbols = {'X', 'O'};
        char[] players = randomCharacter(symbols);
        char player1 = players[0];
        char player2 = (player1 == 'X') ? 'O' : 'X';

        System.out.println("Player 1 you got: " + player1);
        System.out.println("Player 2 you got: " + player2);

    }

    private char[] randomCharacter(char[] symbols) {
        char[] players = new char[2];
        players[0] = randomCharacters(symbols); //to bedzie pierwszy losowy symbol dla player 1
        players[1] = (players[0] == 'X') ? 'O' : 'X'; //w ten sposob player 2 powinien dostac drugi niewybrany wczesniej symbol?
        return players;
    }

    public static char randomCharacters(char[] availableCharacters) {
        Random random = new Random();
        int index = random.nextInt(availableCharacters.length);
        return availableCharacters[index];
    }

    public static void main(String[] args) {
        new Player();
    }

}


