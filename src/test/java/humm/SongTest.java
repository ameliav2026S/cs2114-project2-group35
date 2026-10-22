package humm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Tests Song validation, equality, hashing, and presentation. */
public class SongTest
{
    /** Valid fields are stored and outer spaces are trimmed. */
    @Test
    public void validSongAndTrim()
    {
        Song song = new Song(" Hello ", " Adele ");
        assertEquals("Hello", song.getTitle());
        assertEquals("Adele", song.getArtist());
        assertEquals("Hello by Adele", song.toString());
    }


    /** All invalid field variants throw informative errors for both fields. */
    @Test
    public void invalidFields()
    {
        String[] invalid =
            { null, "", "   ", "x".repeat(81), "a\tb", "a\nb", "a\rb" };
        for (String value : invalid)
        {
            IllegalArgumentException titleError = assertThrows(
                IllegalArgumentException.class,
                () -> new Song(value, "Valid Artist"));
            assertFalse(titleError.getMessage().isBlank());
            assertTrue(titleError.getMessage().contains("title"));
            IllegalArgumentException artistError = assertThrows(
                IllegalArgumentException.class,
                () -> new Song("Valid Title", value));
            assertFalse(artistError.getMessage().isBlank());
            assertTrue(artistError.getMessage().contains("artist"));
        }
    }


    /** Checks specific messages for each invalid-input category. */
    @Test
    public void errorMessages()
    {
        assertEquals(
            "Please enter a title.",
            assertThrows(
                IllegalArgumentException.class,
                () -> new Song(" ", "A")).getMessage());
        assertEquals(
            "The artist must be 80 characters or fewer.",
            assertThrows(
                IllegalArgumentException.class,
                () -> new Song("T", "a".repeat(81))).getMessage());
        assertEquals(
            "The title can only use plain text, with no tabs or line breaks.",
            assertThrows(
                IllegalArgumentException.class,
                () -> new Song("a\tb", "A")).getMessage());
    }


    /** A blank or missing artist says "an artist", not "a artist". */
    @Test
    public void blankArtistMessageUsesAn()
    {
        assertEquals(
            "Please enter an artist.",
            assertThrows(
                IllegalArgumentException.class,
                () -> new Song("T", " ")).getMessage());
        assertEquals(
            "Please enter an artist.",
            assertThrows(
                IllegalArgumentException.class,
                () -> new Song("T", null)).getMessage());
    }


    /** The title is checked before the artist. */
    @Test
    public void titleValidationComesFirst()
    {
        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> new Song(null, null));
        assertEquals("Please enter a title.", error.getMessage());
    }


    /** Exactly 80 characters is valid in both fields. */
    @Test
    public void boundaryAtEightyCharacters()
    {
        String eighty = "x".repeat(80);
        Song song = new Song(eighty, eighty);
        assertEquals(80, song.getTitle().length());
        assertEquals(80, song.getArtist().length());
    }


    /** Case and repeated spaces do not affect equality or hash codes. */
    @Test
    public void normalizedEqualityAndHashCode()
    {
        Song first = new Song("Blinding Lights", "The Weeknd");
        Song second = new Song("blinding  lights", "the weeknd");
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first, first);
        assertEquals(first.hashCode(), second.hashCode());
    }


    /** Different titles, artists, types, or null are not equal. */
    @Test
    public void unequalSongsAndOtherObjects()
    {
        Song song = new Song("Blinding Lights", "The Weeknd");
        assertNotEquals(song, new Song("Blinding Lights", "Other Artist"));
        assertNotEquals(song, new Song("Other Title", "The Weeknd"));
        assertNotEquals(song, null);
        assertNotEquals(song, "Blinding Lights");
    }
}