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
 * This class tests the Library class with one normal case and bad-input cases per method.
 * 
 * @author Tanvi Bhat
 * @version October 15, 2026
 */
public class LibraryTest
{
    private Library library;

    /**
     * This method sets up an empty object called Library.
     */
    @BeforeEach
    public void setUp()
    {
        library = new Library();
    }

    /**
     * This method checks whether an iterator visits the playlist.
     *
     * @param iterator the iterator
     * @param playlist the playlist to look for
     * @return true if that exact object was visited
     */
    private boolean visits(Iterator<Playlist> iterator, Playlist playlist)
    {
        while (iterator.hasNext())
        {
            if (iterator.next() == playlist)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This method counts how many items an iterator visits.
     *
     * @param iterator the iterator
     * @return the number of items visited
     */
    private int countItems(Iterator<Playlist> iterator)
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
     * This method tests the constructor by checking if it has no playlists and nothing deleted.
     */
    @Test
    public void testConstructor()
    {
        assertEquals(0, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
        assertFalse(library.playlists().hasNext());
        assertFalse(library.deletedPlaylists().hasNext());
    }


    /**
     * This method tests that text adds one playlist and returns it.
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
     * This method tests if creating a playlist with a blank title throws an IllegalArgumentException 
     * This doesn't add anything to the library.
     */
    @Test
    public void testCreatePlaylistBlankTitle()
    {
        assertThrows(IllegalArgumentException.class,
            () -> library.createPlaylist("   ", "Description"));
        assertEquals(0, library.playlistCount());
    }

    /**
     * This tests if the title over 80 characters, if so, it throws an exception.
     */
    @Test
    public void testCreatePlaylistTooLong()
    {
        assertThrows(IllegalArgumentException.class,
            () -> library.createPlaylist("Gym", "a".repeat(81)));
        assertEquals(0, library.playlistCount());
    }


    /**
     * This tests deleting a playlist with 3 songs and moves it to recently deleted.
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
     * This method tests if two playlists with the same title.
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
     * This method tests deleting a playlist never created in the Library, if so, it throws an exception.
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
     * This method tests if deleting the same playlist twice throws and exception the second time.
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


    /**
     * This method tests if restoring a playlist brings the playlist back with its songs and its own
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
     * This method tests if restoring a playlist that isn't deleted throws.
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


    /**
     * This method tests if clearing 2 playlists moves both to Recently Deleted.
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
     * This method tests if clearing with no playlists does nothing and doesn't throw an exception.
     */
    @Test
    public void testClearPlaylistsEmpty()
    {
        library.clearPlaylists();
        assertEquals(0, library.playlistCount());
        assertEquals(0, library.deletedPlaylistCount());
    }


    /**
     * This method tests if emptying Recently Deleted removes those playlists for good.
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
     * This method tests if emptying an already empty Recently Deleted and doesn't throw an exception.
     */
    @Test
    public void testEmptyDeletedPlaylistsEmpty()
    {
        library.emptyDeletedPlaylists();
        assertEquals(0, library.deletedPlaylistCount());
    }


    /**
     * This method tests if the playlists iterator visits each playlist once.
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
     * This method tests if next() passes the end of an empty iterator and throws an exception.
     */
    @Test
    public void testIteratorPastEnd()
    {
        Iterator<Playlist> it = library.deletedPlaylists();
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}