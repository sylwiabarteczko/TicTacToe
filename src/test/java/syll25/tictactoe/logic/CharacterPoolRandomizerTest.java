package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.logic.CharacterPoolRandomizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class CharacterPoolRandomizerTest {

    @Test
    public void testDrawSymbol() {

        //given
        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');
        Set<Character> availableSymbols = new HashSet<>(Arrays.asList('X', 'Y', 'Z', 'O', 'S'));

        //when
        char drawSymbol = symbolChoice.drawSymbol();

        //then
        assertTrue(drawSymbol == 'X' || drawSymbol == 'Y' || drawSymbol == 'Z' || drawSymbol == 'O' || drawSymbol == 'S');
        assertTrue(availableSymbols.contains(drawSymbol), "Symbol is not available");
    }
}