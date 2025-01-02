package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlState implements State{

    private final String filename;

    public XmlState(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(GameBoard board, Player player1, Player player2) {
        StateDTO stateDTO = new StateDTO();
        try {
            JAXBContext context = JAXBContext.newInstance(StateDTO.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(stateDTO, new File(filename));
        } catch (JAXBException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public StateDTO load() {
        try {
            JAXBContext context = JAXBContext.newInstance(StateDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (StateDTO) unmarshaller.unmarshal(new File(filename));
        } catch (JAXBException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public StateDTO load(Long gameId, Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        return null;
    }
}

