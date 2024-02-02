public class Player {

    public Player() {
        Random random = new Random();
        symbol = (random.nextBoolean() ? 'X' : 'O');
    }
    public char newSymbol() {
        return symbol;
    }


}