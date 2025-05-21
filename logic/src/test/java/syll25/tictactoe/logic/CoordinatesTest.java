package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.logic.exception.InvalidCoordinatesException;

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

        coordinates = new Coordinates("A3");
        assertEquals(0, coordinates.getCol());
        assertEquals(2, coordinates.getRow());

        coordinates = new Coordinates("C1");
        assertEquals(2, coordinates.getCol());
        assertEquals(0, coordinates.getRow());

    }

    @Test
    public void testInvalidCoordinatesException() {
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("A"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("1"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("11A"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("B22"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("-1C"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("A10"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("C20"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("Ana"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("!@#"));
        assertThrows(InvalidCoordinatesException.class, () -> new Coordinates("123"));
    }

    @Test
    public void testConvertRowColInput() {
        Coordinates coordinates = new Coordinates("A2");
        assertEquals(0, coordinates.getCol());
        assertEquals(1, coordinates.getRow());

        coordinates = new Coordinates("3B");
        assertEquals(1, coordinates.getCol());
        assertEquals(2, coordinates.getRow());

    }
}