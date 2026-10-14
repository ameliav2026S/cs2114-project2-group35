
package humm;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests generic chain operations and all documented failure paths. */
public class LinkedChainTest
{
    private LinkedChain<String> chain;

    /** Sets up a fresh chain for each test. */
    @BeforeEach
    public void setUp()
    {
        chain = new LinkedChain<>();
    }


    /** Verifies the empty constructor and empty state. */
    @Test
    public void newChainIsEmpty()
    {
        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
        assertEquals("[]", chain.toString());
    }


    /** Checks insertion, null rejection and duplicate counting. */
    @Test
    public void addAndDuplicates()
    {
        assertTrue(chain.add("A"));
        assertTrue(chain.add("B"));
        assertEquals(2, chain.size());
        assertTrue(chain.contains("A"));
        assertTrue(chain.contains("B"));
        assertThrows(IllegalArgumentException.class, () -> chain.add(null));
        assertEquals(2, chain.size());
        chain.add("A");
        assertEquals(3, chain.size());
        assertEquals(2, chain.count("A"));
    }


    /** Removes a middle node without losing its neighbors. */
    @Test
    public void removeMiddle()
    {
        chain.add("A");
        chain.add("B");
        chain.add("C");
        chain.remove("B");
        assertEquals(2, chain.size());
        assertFalse(chain.contains("B"));
        assertTrue(chain.contains("A"));
        assertTrue(chain.contains("C"));
    }


    /** Removes head and tail nodes correctly. */
    @Test
    public void removeHeadAndTail()
    {
        chain.add("A");
        chain.add("B");
        chain.add("C");
        chain.remove("C");
        chain.remove("A");
        assertEquals(1, chain.size());
        assertEquals("[B]", chain.toString());
        chain.remove("B");
        assertTrue(chain.isEmpty());
    }


    /** A failed removal throws without changing the chain. */
    @Test
    public void removeFailures()
    {
        assertThrows(NoSuchElementException.class, () -> chain.remove("A"));
        chain.add("A");
        assertThrows(
            NoSuchElementException.class,
            () -> chain.remove("missing"));
        assertThrows(NoSuchElementException.class, () -> chain.remove(null));
        assertEquals(1, chain.size());
        assertTrue(chain.contains("A"));
    }


    /** Only one duplicate is removed per call. */
    @Test
    public void removeOneDuplicate()
    {
        chain.add("A");
        chain.add("A");
        chain.remove("A");
        assertEquals(1, chain.size());
        assertEquals(1, chain.count("A"));
    }


    /** Covers present, missing, null, and empty searches and counts. */
    @Test
    public void containsAndCount()
    {
        assertFalse(chain.contains("A"));
        assertFalse(chain.contains(null));
        assertEquals(0, chain.count(null));
        assertEquals(0, chain.count("A"));
        chain.add("A");
        chain.add("B");
        chain.add("A");
        assertTrue(chain.contains("A"));
        assertFalse(chain.contains("C"));
        assertEquals(2, chain.count("A"));
        assertEquals(0, chain.count("C"));
    }


    /** Counts entries correctly after a removal. */
    @Test
    public void sizeAndIsEmptyAfterRemoval()
    {
        chain.add("A");
        chain.add("B");
        chain.add("C");
        chain.remove("B");
        assertEquals(2, chain.size());
        assertFalse(chain.isEmpty());
    }


    /** Iteration visits each item once without assuming order. */
    @Test
    public void iteratorVisitsAll()
    {
        chain.add("A");
        chain.add("B");
        chain.add("C");
        Set<String> seen = new HashSet<>();
        int visits = 0;
        for (String item : chain)
        {
            assertTrue(seen.add(item));
            visits++;
        }
        assertEquals(3, visits);
        assertEquals(Set.of("A", "B", "C"), seen);
    }


    /** Empty iteration and exhausted next() work as specified. */
    @Test
    public void iteratorEmptyAndExhausted()
    {
        Iterator<String> it = chain.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
        int visits = 0;
        for (String ignored : chain)
        {
            visits++;
        }
        assertEquals(0, visits);
        chain.add("A");
        it = chain.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());
        Iterator<String> exhausted = it;
        assertThrows(NoSuchElementException.class, exhausted::next);
    }


    /** A new iterator reflects the chain after a removal. */
    @Test
    public void iteratorAfterRemoval()
    {
        chain.add("A");
        chain.add("B");
        chain.remove("A");
        Set<String> seen = new HashSet<>();
        for (String item : chain)
        {
            seen.add(item);
        }
        assertEquals(Set.of("B"), seen);
    }


    /** Clear is safe both for full and already empty chains. */
    @Test
    public void clearFullAndEmpty()
    {
        chain.add("A");
        chain.add("B");
        chain.clear();
        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
        assertFalse(chain.contains("A"));
        chain.clear();
        assertEquals("[]", chain.toString());
    }


    /** Shows that a single generic class handles songs and playlists. */
    @Test
    public void genericReuseWithSongAndPlaylist()
    {
        LinkedChain<Song> songs = new LinkedChain<>();
        LinkedChain<Playlist> playlists = new LinkedChain<>();
        Song song = new Song("Blinding Lights", "The Weeknd");
        Playlist playlist = new Playlist("Favorites", "My songs");
        songs.add(song);
        playlists.add(playlist);
        assertTrue(songs.contains(new Song("blinding  lights", "the weeknd")));
        assertTrue(playlists.contains(playlist));
        assertEquals(1, songs.size());
        assertEquals(1, playlists.size());
        songs.remove(new Song("BLINDING LIGHTS", "THE WEEKND"));
        playlists.remove(playlist);
        assertFalse(songs.contains(song));
        assertFalse(playlists.contains(playlist));
    }


    /** Repeats insertion and duplicate removal using song equality. */
    @Test
    public void songDuplicates()
    {
        LinkedChain<Song> songs = new LinkedChain<>();
        Song original = new Song("Hello", "Adele");
        Song equivalent = new Song(" hello ", "ADELE");
        songs.add(original);
        songs.add(equivalent);
        assertEquals(2, songs.count(original));
        songs.remove(original);
        assertEquals(1, songs.count(equivalent));
        assertTrue(songs.contains(original));
    }
}
