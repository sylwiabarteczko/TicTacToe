package syll25.tictactoe.logic.state;

public class StateFactory {
    public static State getState(String filename) {
        if (filename.endsWith(".json")) {
            return new JsonState(filename);
        } else if (filename.endsWith(".xml")) {
            return new XmlState(filename);
        } else if (filename.equalsIgnoreCase("sqlite")) {
            return new SqliteState();
        } else {
            return new TxtState(filename);
        }
    }
}
