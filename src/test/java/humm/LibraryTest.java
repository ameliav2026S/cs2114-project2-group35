package humm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Library: one normal case and bad-input cases per method,
 * following section 8 of the Humm spec.
 *
 * @author Tanvi Bhat
 * @version October 2026
 */
public class LibraryTest
{
    private Library library;

    /**
     * Makes an empty Library before each test.
     */
    @BeforeEach
    public void setUp()
    {
        library = new Library();
    }

    /**
     * Checks whether an iterator visits this exact playlist.
     *
     * @param it       the iterator
     * @param playlist the playlist to look for
     * @return true if that exact object was visited
     */
    private boolean visits(Iterator<Playlist> it, Playlist playlist)
    {
        while (it.hasNext())
        {
            if (it.next() == playlist)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts how many items an iterator visits.
     *
     * @param it the iterator
     * @return the number of items visited
     */
    private int countItems(Iterator<Playlist> it)
    {
        int count = 0;
        while (it.hasNext())
        {
            it.next();
            count++;
        }
        return count;
    }

    // ----- constructor -----

    /**
     * A new Library has no playlists and nothing deleted.
     */
    @Test
    public void testConstructor()
    {
        assertEquals(0, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
        assertFalse(library.playlists().hasNext());
        assertFalse(library.deletedPlaylists().hasNext());
    }

    // ----- createPlaylist -----

    /**
     * Valid text adds one playlist and returns it.
     */
    @Test
    public void testCreatePlaylist()
    {
        Playlist made = library.createPlaylist("Gym", "Hype songs");
        assertEquals(1, library.playlistCount());
        assertEquals("Gym", made.getTitle());
        assertTrue(visits(library.playlists(), made));
    }

    /**
     * A blank title throws, and the count doesn't change.
     */
    @Test
    public void testCreatePlaylistBlankTitle()
    {
        assertThrows(IllegalArgumentException.class,
            () -> library.createPlaylist("   ", "Description"));
        assertEquals(0, library.playlistCount());
    }

    /**
     * Text over 80 characters throws, and the count doesn't change.
     */
    @Test
    public void testCreatePlaylistTooLong()
    {
        assertThrows(IllegalArgumentException.class,
            () -> library.createPlaylist("Gym", "a".repeat(81)));
        assertEquals(0, library.playlistCount());
    }

    // ----- deletePlaylist -----

    /**
     * Deleting a playlist with 3 songs moves it, songs and all, to
     * Recently Deleted.
     */
    @Test
    public void testDeletePlaylist()
    {
        Playlist gym = library.createPlaylist("Gym", "Hype songs");
        gym.addSong(new Song("Stronger", "Kanye West"));
        gym.addSong(new Song("Lose Yourself", "Eminem"));
        gym.addSong(new Song("Eye of the Tiger", "Survivor"));
        library.deletePlaylist(gym);
        assertEquals(0, library.playlistCount());
        assertEquals(1, library.deletedPlaylistCount());
        assertFalse(visits(library.playlists(), gym));
        assertTrue(visits(library.deletedPlaylists(), gym));
        assertEquals(3, gym.songCount());
    }

    /**
     * Two playlists with the same title: deleting one leaves the other.
     */
    @Test
    public void testDeletePlaylistSameTitle()
    {
        Playlist first = library.createPlaylist("Getting Ready", "Mine");
        Playlist second = library.createPlaylist("Getting Ready", "Mine");
        library.deletePlaylist(first);
        assertEquals(1, library.playlistCount());
        assertTrue(visits(library.playlists(), second));
        assertFalse(visits(library.playlists(), first));
    }

    /**
     * Deleting a playlist never created in this Library throws.
     */
    @Test
    public void testDeletePlaylistNotInLibrary()
    {
        library.createPlaylist("Gym", "Hype songs");
        Playlist outsider = new Playlist("Gym", "Hype songs");
        assertThrows(NoSuchElementException.class,
            () -> library.deletePlaylist(outsider));
        assertEquals(1, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
    }

    /**
     * Deleting the same playlist twice throws the second time.
     */
    @Test
    public void testDeletePlaylistTwice()
    {
        Playlist gym = library.createPlaylist("Gym", "Hype songs");
        library.deletePlaylist(gym);
        assertThrows(NoSuchElementException.class,
            () -> library.deletePlaylist(gym));
        assertEquals(1, library.deletedPlaylistCount());
    }

    // ----- restorePlaylist -----

    /**
     * Restoring brings the playlist back with its songs and its own
     * deleted songs.
     */
    @Test
    public void testRestorePlaylist()
    {
        Playlist gym = library.createPlaylist("Gym", "Hype songs");
        Song stronger = new Song("Stronger", "Kanye West");
        gym.addSong(stronger);
        gym.addSong(new Song("Lose Yourself", "Eminem"));
        gym.addSong(new Song("Eye of the Tiger", "Survivor"));
        gym.deleteSong(stronger);
        library.deletePlaylist(gym);
        library.restorePlaylist(gym);
        assertEquals(1, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
        Iterator<Playlist> it = library.playlists();
        Playlist restored = it.next();
        assertSame(gym, restored);
        assertEquals(2, restored.songCount());
        assertEquals(1, restored.deletedCount());
    }

    /**
     * Restoring a playlist that isn't deleted throws.
     */
    @Test
    public void testRestorePlaylistNotDeleted()
    {
        Playlist gym = library.createPlaylist("Gym", "Hype songs");
        assertThrows(NoSuchElementException.class,
            () -> library.restorePlaylist(gym));
        assertEquals(1, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
    }

    // ----- clearPlaylists -----

    /**
     * Clearing 2 playlists moves both to Recently Deleted.
     */
    @Test
    public void testClearPlaylists()
    {
        Playlist gym = library.createPlaylist("Gym", "Hype songs");
        Playlist study = library.createPlaylist("Study", "Calm songs");
        library.clearPlaylists();
        assertEquals(0, library.playlistCount());
        assertEquals(2, library.deletedPlaylistCount());
        assertTrue(visits(library.deletedPlaylists(), gym));
        assertTrue(visits(library.deletedPlaylists(), study));
    }

    /**
     * Clearing with no playlists does nothing and doesn't throw.
     */
    @Test
    public void testClearPlaylistsEmpty()
    {
        library.clearPlaylists();
        assertEquals(0, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
    }

    // ----- emptyDeletedPlaylists -----

    /**
     * Emptying Recently Deleted removes those playlists for good.
     */
    @Test
    public void testEmptyDeletedPlaylists()
    {
        Playlist gym = library.createPlaylist("Gym", "Hype songs");
        library.createPlaylist("Study", "Calm songs");
        library.clearPlaylists();
        library.emptyDeletedPlaylists();
        assertEquals(0, library.deletedPlaylistCount());
        assertThrows(NoSuchElementException.class,
            () -> library.restorePlaylist(gym));
    }

    /**
     * Emptying an already empty Recently Deleted doesn't throw.
     */
    @Test
    public void testEmptyDeletedPlaylistsEmpty()
    {
        library.emptyDeletedPlaylists();
        assertEquals(0, library.deletedPlaylistCount());
    }

    // ----- iterators -----

    /**
     * The playlists iterator visits each playlist once.
     */
    @Test
    public void testPlaylistsIterator()
    {
        library.createPlaylist("Gym", "Hype songs");
        library.createPlaylist("Study", "Calm songs");
        library.createPlaylist("Road Trip", "Sing-alongs");
        assertEquals(3, countItems(library.playlists()));
    }

    /**
     * next() past the end of an empty iterator throws.
     */
    @Test
    public void testIteratorPastEnd()
    {
        Iterator<Playlist> it = library.deletedPlaylists();
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}