package syll25.tictactoe.logic.state;

public class StateFactory {
    public static State getState(String filename) {
        if (filename.endsWith(".json")) {
            return new JacksonJsonState(filename);
        } else if (filename.endsWith(".xml")) {
            return new JaxXmlState(filename);
        } else {
            return new TxtState(filename);
        }
    }
}
