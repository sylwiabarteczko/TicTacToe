package syll25.tictactoe;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class CharacterPoolRandomizerTest {

    @Test
    public void testDrawSymbol() {


        //given
        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');
        //when
        List<Character> characters = symbolChoice.drawSymbols();

        //then
        assertNotNull(characters);

        assertEquals(5, characters.size());

        assertTrue(characters.contains('X'));

        assertTrue(characters.contains('Y'));

        assertTrue(characters.contains('Z'));

        assertTrue(characters.contains('O'));

        assertTrue(characters.contains('S'));


    }
}