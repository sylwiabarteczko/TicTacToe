package syll25.tictactoe.logic.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StateFactoryTest {

    @Test
    public void testJsonState() {
        State state = StateFactory.getState("game.json");
        assertTrue(state instanceof JsonState);
    }
    @Test
    public void testTxtState() {
        State state = StateFactory.getState("game.txt");
        assertTrue(state instanceof TxtState);
    }
    @Test
    public void testXmlState() {
        State state = StateFactory.getState("game.xml");
        assertTrue(state instanceof XmlState);
    }
    @Test
    public void testSqliteState() {
        State state = StateFactory.getState("sqlite");
        assertTrue(state instanceof SqliteState);
    }
}