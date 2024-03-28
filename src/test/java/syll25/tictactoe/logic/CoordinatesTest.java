package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesTest {

    @Test
    public void testValidCoordinates() {
        Coordinates coordinates = new Coordinates("A1");
        assertEquals(0, coordinates.getCol());
        assertEquals(0, coordinates.getRow());

        coordinates = new Coordinates("B2");
        assertEquals(1, coordinates.getCol());
        assertEquals(1, coordinates.getRow());

    }

}
