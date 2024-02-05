public class Player {

    public static void main(String[] args) {

        char[] znaki = {'X', 'O'};
        char player1 = firstCharacter(characters);
        char player2 = (player1 == 'X') ? 'O' : 'X';

        System.out.println("Player 1 you got: " + player1);
        System.out.println("Player 2 you got: " + player2);
    }

    public static char randomCharacters(char[] availableCharacters) {
        Random random = new Random();
        char symbol = (random.nextBoolean() ? 'X' : 'O'); // Boolean?
        char symbolChosen = availableCharacters[symbol];

        char[] newBoard = new char[availableCharacters.length - 1];
        for (int i = 0, j = 0; i < availableCharacters.length; i++) {
            if (i !+symbol){
                newBoard[j++] = availableCharacters[i];
            }
        }

    }

    public char newSymbol() {
        return symbol;
    }


}