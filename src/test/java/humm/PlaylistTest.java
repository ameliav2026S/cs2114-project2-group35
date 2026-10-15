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
 * This class tests the Playlist class with one normal case and bad-input
 * cases per method.
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
     * This method sets up an empty playlist and three songs before each
     * test.
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
     * This method counts how many songs an iterator visits.
     *
     * @param iterator the iterator
     * @return the number of songs visited
     */
    private int countItems(Iterator<Song> iterator)
    {
        int count = 0;
        while (iterator.hasNext())
        {
            iterator.next();
            count++;
        }
        return count;
    }

    /**
     * This method checks whether an iterator visits a song equal to the
     * given one.
     *
     * @param iterator the iterator
     * @param song the song to look for
     * @return true if the song was found
     */
    private boolean visits(Iterator<Song> iterator, Song song)
    {
        while (iterator.hasNext())
        {
            if (iterator.next().equals(song))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * This method tests the constructor by checking that the title and
     * description are stored and both chains start empty.
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
     * This method tests if a blank title throws an
     * IllegalArgumentException with a helpful message.
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
     * This method tests if a missing description throws an
     * IllegalArgumentException with a helpful message.
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
     * This method tests if a title over 80 characters throws an exception.
     */
    @Test
    public void testConstructorTooLong()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new Playlist("a".repeat(81), "Description"));
    }

    /**
     * This method tests if a pasted tab or line break throws an exception.
     */
    @Test
    public void testConstructorTabOrLineBreak()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new Playlist("Getting\tReady", "Description"));
        assertThrows(IllegalArgumentException.class,
            () -> new Playlist("Getting Ready", "Line\nbreak"));
    }


    /**
     * This method tests if adding two songs gives a count of 2.
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
     * This method tests if the same song can be added twice.
     */
    @Test
    public void testAddSongDuplicate()
    {
        playlist.addSong(lights);
        playlist.addSong(new Song("blinding lights", "the weeknd"));
        assertEquals(2, playlist.songCount());
    }

    /**
     * This method tests if adding null throws an exception and doesn't
     * change anything.
     */
    @Test
    public void testAddSongNull()
    {
        playlist.addSong(lights);
        assertThrows(IllegalArgumentException.class,
            () -> playlist.addSong(null));
        assertEquals(1, playlist.songCount());
    }

    /**
     * This method tests if deleting one of two songs moves it to Recently
     * Deleted.
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
     * This method tests if a song typed with different capitals still
     * finds the match and gets deleted.
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
     * This method tests if deleting a song with two copies only removes
     * one of them.
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
     * This method tests if deleting a song not in the playlist throws an
     * exception and doesn't change the counts.     
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
     * This method tests if deleting from an empty playlist throws an
     * exception.
     */
    @Test
    public void testDeleteSongEmpty()
    {
        assertThrows(NoSuchElementException.class,
            () -> playlist.deleteSong(lights));
        assertEquals(0, playlist.deletedCount());
    }


    /**
     * This method tests if restoring a deleted song puts it back in the
     * playlist.
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
     * This method tests if restoring a song that isn't deleted throws an
     * exception and doesn't change the counts.     
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


    /**
     * This method tests if clearing 3 songs moves all of them to Recently
     * Deleted.
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
     * This method tests if clearing an empty playlist does nothing and
     * doesn't throw an exception.
     */
    @Test
    public void testClearSongsEmpty()
    {
        playlist.clearSongs();
        assertEquals(0, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }


    /**
     * This method tests if emptying Recently Deleted removes those songs
     * for good.     
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
     * This method tests if emptying an already empty Recently Deleted
     * doesn't throw an exception.
     */
    @Test
    public void testEmptyDeletedSongsEmpty()
    {
        playlist.emptyDeletedSongs();
        assertEquals(0, playlist.deletedCount());
    }


    /**
     * This method tests if restoring all moves every deleted song back
     * into the playlist (stretch goal).
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
    * This method tests if restoring all with nothing deleted does nothing
    * and doesn't throw an exception.     
    */
    @Test
    public void testRestoreAllSongsEmpty()
    {
        playlist.addSong(lights);
        playlist.restoreAllSongs();
        assertEquals(1, playlist.songCount());
        assertEquals(0, playlist.deletedCount());
    }


    /**
     * This method tests if the songs iterator visits each song once.
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
     * This method tests if iterators over empty chains visit nothing, and
     * if calling next() past the end throws an exception.
     */
    @Test
    public void testIteratorsEmpty()
    {
        assertEquals(0, countItems(playlist.songs()));
        Iterator<Song> deleted = playlist.deletedSongs();
        assertFalse(deleted.hasNext());
        assertThrows(NoSuchElementException.class, () -> deleted.next());
    }

    /**
     * This method tests if two playlists with the same title and
     * description are not equal, since playlists are compared by identity.
     */
    @Test
    public void testPlaylistEqualityIsIdentity()
    {
        Playlist twin = new Playlist("Getting Ready", "Songs for getting ready");
        assertNotEquals(playlist, twin);
        assertEquals(playlist, playlist);
    }
}