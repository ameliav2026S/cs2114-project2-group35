package humm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Playlist: one normal case and bad-input cases per method,
 * following section 8 of the Humm spec.
 *
 * @author Tanvi Bhat
 * @version October 2026
 */
public class PlaylistTest
{
    private Playlist playlist;
    private Song lights;
    private Song espresso;
    private Song levitating;

    /**
     * Makes an empty playlist and three songs before each test.
     */
    @BeforeEach
    public void setUp()
    {
        playlist = new Playlist("Getting Ready", "Songs for getting ready");
        lights = new Song("Blinding Lights", "The Weeknd");
        espresso = new Song("Espresso", "Sabrina Carpenter");
        levitating = new Song("Levitating", "Dua Lipa");
    }

    /**
     * Counts how many items an iterator visits.
     *
     * @param it the iterator
     * @return the number of items visited
     */
    private int countItems(Iterator<Song> it)
    {
        int count = 0;
        while (it.hasNext())
        {
            it.next();
            count++;
        }
        return count;
    }

    /**
     * Checks whether an iterator visits a song equal to the given one.
     *
     * @param it   the iterator
     * @param song the song to look for
     * @return true if it was found
     */
    private boolean visits(Iterator<Song> it, Song song)
    {
        while (it.hasNext())
        {
            if (it.next().equals(song))
            {
                return true;
            }
        }
        return false;
    }

    // ----- constructor -----

    /**
     * Valid title and description are stored, and both chains start
     * empty.
     */
    @Test
    public void testConstructorValid()
    {
        assertEquals("Getting Ready", playlist.getTitle());
        assertEquals("Songs for getting ready", playlist.getDescription());
        assertEquals(0, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }

    /**
     * A blank title is rejected with a helpful message.
     */
    @Test
    public void testConstructorBlankTitle()
    {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> new Playlist("  ", "Description"));
        assertEquals("Please enter a title.", e.getMessage());
    }

    /**
     * A blank description is rejected with a helpful message.
     */
    @Test
    public void testConstructorBlankDescription()
    {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> new Playlist("Title", null));
        assertEquals("Please enter a description.", e.getMessage());
    }

    /**
     * A title over 80 characters is rejected.
     */
    @Test
    public void testConstructorTooLong()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new Playlist("a".repeat(81), "Description"));
    }

    /**
     * A pasted tab or line break is rejected.
     */
    @Test
    public void testConstructorTabOrLineBreak()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new Playlist("Getting\tReady", "Description"));
        assertThrows(IllegalArgumentException.class,
            () -> new Playlist("Getting Ready", "Line\nbreak"));
    }

    // ----- addSong -----

    /**
     * Adding two songs gives a count of 2.
     */
    @Test
    public void testAddSong()
    {
        playlist.addSong(lights);
        playlist.addSong(espresso);
        assertEquals(2, playlist.songCount());
        assertTrue(visits(playlist.songs(), lights));
        assertTrue(visits(playlist.songs(), espresso));
    }

    /**
     * The same song can be added twice.
     */
    @Test
    public void testAddSongDuplicate()
    {
        playlist.addSong(lights);
        playlist.addSong(new Song("blinding lights", "the weeknd"));
        assertEquals(2, playlist.songCount());
    }

    /**
     * Adding null throws and changes nothing.
     */
    @Test
    public void testAddSongNull()
    {
        playlist.addSong(lights);
        assertThrows(IllegalArgumentException.class,
            () -> playlist.addSong(null));
        assertEquals(1, playlist.songCount());
    }

    // ----- deleteSong -----

    /**
     * Deleting one of two songs moves it to Recently Deleted.
     */
    @Test
    public void testDeleteSong()
    {
        playlist.addSong(lights);
        playlist.addSong(espresso);
        playlist.deleteSong(lights);
        assertEquals(1, playlist.songCount());
        assertEquals(1, playlist.deletedCount());
        assertFalse(visits(playlist.songs(), lights));
        assertTrue(visits(playlist.deletedSongs(), lights));
    }

    /**
     * A song typed with different capitals still finds the match.
     */
    @Test
    public void testDeleteSongTypedDifferently()
    {
        playlist.addSong(lights);
        playlist.deleteSong(new Song("BLINDING LIGHTS", "the weeknd"));
        assertEquals(0, playlist.songCount());
        assertEquals(1, playlist.deletedCount());
    }

    /**
     * With two equal songs, deleting removes only one.
     */
    @Test
    public void testDeleteSongDuplicateRemovesOne()
    {
        playlist.addSong(lights);
        playlist.addSong(lights);
        playlist.deleteSong(lights);
        assertEquals(1, playlist.songCount());
        assertEquals(1, playlist.deletedCount());
    }

    /**
     * Deleting a song not in the playlist throws, and counts don't change.
     */
    @Test
    public void testDeleteSongMissing()
    {
        playlist.addSong(lights);
        assertThrows(NoSuchElementException.class,
            () -> playlist.deleteSong(espresso));
        assertEquals(1, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }

    /**
     * Deleting from an empty playlist throws.
     */
    @Test
    public void testDeleteSongEmpty()
    {
        assertThrows(NoSuchElementException.class,
            () -> playlist.deleteSong(lights));
        assertEquals(0, playlist.deletedCount());
    }

    // ----- restoreSong -----

    /**
     * Restoring a deleted song puts it back in songs.
     */
    @Test
    public void testRestoreSong()
    {
        playlist.addSong(lights);
        playlist.deleteSong(lights);
        playlist.restoreSong(lights);
        assertEquals(1, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
        assertTrue(visits(playlist.songs(), lights));
    }

    /**
     * Restoring a song that isn't deleted throws, and counts don't change.
     */
    @Test
    public void testRestoreSongNotDeleted()
    {
        playlist.addSong(lights);
        assertThrows(NoSuchElementException.class,
            () -> playlist.restoreSong(lights));
        assertEquals(1, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }

    // ----- clearSongs -----

    /**
     * Clearing 3 songs moves all of them to Recently Deleted.
     */
    @Test
    public void testClearSongs()
    {
        playlist.addSong(lights);
        playlist.addSong(espresso);
        playlist.addSong(levitating);
        playlist.clearSongs();
        assertEquals(0, playlist.songCount());
        assertEquals(3, playlist.deletedCount());
        assertTrue(visits(playlist.deletedSongs(), espresso));
    }

    /**
     * Clearing an empty playlist does nothing and doesn't throw.
     */
    @Test
    public void testClearSongsEmpty()
    {
        playlist.clearSongs();
        assertEquals(0, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }

    // ----- emptyDeletedSongs -----

    /**
     * Emptying Recently Deleted removes those songs for good.
     */
    @Test
    public void testEmptyDeletedSongs()
    {
        playlist.addSong(lights);
        playlist.addSong(espresso);
        playlist.clearSongs();
        playlist.emptyDeletedSongs();
        assertEquals(0, playlist.deletedCount());
        assertThrows(NoSuchElementException.class,
            () -> playlist.restoreSong(lights));
    }

    /**
     * Emptying an already empty Recently Deleted doesn't throw.
     */
    @Test
    public void testEmptyDeletedSongsEmpty()
    {
        playlist.emptyDeletedSongs();
        assertEquals(0, playlist.deletedCount());
    }

    // ----- restoreAllSongs (stretch) -----

    /**
     * Restore all moves every deleted song back.
     */
    @Test
    public void testRestoreAllSongs()
    {
        playlist.addSong(lights);
        playlist.addSong(espresso);
        playlist.clearSongs();
        playlist.restoreAllSongs();
        assertEquals(2, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }

    /**
     * Restore all with nothing deleted does nothing and doesn't throw.
     */
    @Test
    public void testRestoreAllSongsEmpty()
    {
        playlist.addSong(lights);
        playlist.restoreAllSongs();
        assertEquals(1, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }

    // ----- iterators -----

    /**
     * The songs iterator visits each song once.
     */
    @Test
    public void testSongsIterator()
    {
        playlist.addSong(lights);
        playlist.addSong(espresso);
        playlist.addSong(levitating);
        assertEquals(3, countItems(playlist.songs()));
    }

    /**
     * Iterators over empty chains visit nothing, and next() past the end
     * throws.
     */
    @Test
    public void testIteratorsEmpty()
    {
        assertEquals(0, countItems(playlist.songs()));
        Iterator<Song> deleted = playlist.deletedSongs();
        assertFalse(deleted.hasNext());
        assertThrows(NoSuchElementException.class, () -> deleted.next());
    }

    // ----- equality -----

    /**
     * Playlists use identity: two playlists with the same title and
     * description are not equal.
     */
    @Test
    public void testPlaylistEqualityIsIdentity()
    {
        Playlist twin = new Playlist("Getting Ready", "Songs for getting ready");
        assertNotEquals(playlist, twin);
        assertEquals(playlist, playlist);
    }
}