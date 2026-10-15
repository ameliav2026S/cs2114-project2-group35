package humm;

import java.util.Iterator;

/**
 * This class holds the logic for one playlist. 
 *  It stores the two LinkedChain<Song> chains: 
 * One for the songs in the playlist and the other for the ones that are recently deleted.
 * 
 * What it does:
 * Checks its own title and description when it is created
 * Adds songs (duplicates are allowed)
 * Deletes a song by moving it into Recently Deleted
 * Restores a deleted song back into the playlist
 * Clears all songs into Recently Deleted at once
 * Empties Recently Deleted for good
 * Lets other classes loop over the songs and get counts, without
 * touching the LinkedChain directly
 *
 * Playlist does not override equals() on purpose. Two playlists are only
 * equal if they are the same object, so deleting one playlist never
 * deletes a different playlist with the same title.
 * Logic: Library -> Playlist -> LinkedChain
 * 
 * @author Tanvi Bhat
 * @version October 15, 2026
 */
public class Playlist
{
    /** Longest allowed title or description, in characters. */
    public static final int MAX_LENGTH = 80;

    private final String title;
    private final String description;
    private final LinkedChain<Song> songs;
    private final LinkedChain<Song> deletedSongs;

    /**
     * Creates a playlist with both chains empty, after checking its title
     * and description.
     *
     * @param title the playlist's title
     * @param description the playlist's description
     * @throws IllegalArgumentException if either field is blank, longer
     * than 80 characters, or has a tab or line break
     */
    public Playlist(String title, String description)
    {
        this.title = clean(title, "title");
        this.description = clean(description, "description");
        songs = new LinkedChain<Song>();
        deletedSongs = new LinkedChain<Song>();
    }

    /**
     * This metho gets the playlist's title.
     *
     * @return the cleaned title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * This metod gets the playlist's description.
     *
     * @return the cleaned description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * This method adds a song to this playlist. The same song can be added more than once.
     *
     * @param song the song to add
     * @throws IllegalArgumentException if the song is null
     */
    public void addSong(Song song)
    {
        if (song == null)
        {
            throw new IllegalArgumentException("Cannot add a missing song.");
        }
        songs.add(song);
    }

    /**
     * This method moves one matching song from songs into the deletedSongs
     *
     * @param song the song to delete
     * @throws java.util.NoSuchElementException if no matching song is in
     *         the playlist
     */
    public void deleteSong(Song song)
    {
        songs.remove(song);
        deletedSongs.add(song);
    }

    /**
     * This method moves one matching song out of deletedSongs and back into the playlist.
     *
     * @param song the song to restore
     * @throws java.util.NoSuchElementException if no matching song is in
     *         Recently Deleted
     */
    public void restoreSong(Song song)
    {
        deletedSongs.remove(song);
        songs.add(song);
    }

    /**
     * This method moves all songs into deletedSongs, then empties the song chain.
     */
    public void clearSongs()
    {
        for (Song song : songs)
        {
            deletedSongs.add(song);
        }
        songs.clear();
    }

    /**
     * This method empties the Recently Deleted for good.
     */
    public void emptyDeletedSongs()
    {
        deletedSongs.clear();
    }

    /**
     * This method (stretch goal) moves all the songs in Recently Deleted
     * back into the playlist, then empties Recently Deleted.
     */
    public void restoreAllSongs()
    {
        for (Song song : deletedSongs)
        {
            songs.add(song);
        }
        deletedSongs.clear();
    }

    /**
     * This method returns an iterator that steps through the songs in the
     * playlist one at a time, so other classes can look at each song
     * without touching the chain.
     *
     * @return an iterator over the songs
     */
    public Iterator<Song> songs()
    {
        return songs.iterator();
    }

    /**
     * This method returns an iterator that steps through the deleted songs
     * one at a time, so other classes can look at each deleted song
     * without touching the chain.
     *
     * @return an iterator over the deleted songs
     */
    public Iterator<Song> deletedSongs()
    {
        return deletedSongs.iterator();
    }

    /**
     * This method gets how many songs are in the playlist.
     *
     * @return the number of songs
     */
    public int songCount()
    {
        return songs.size();
    }

    /**
     * This method gets how many songs are in Recently Deleted.
     *
     * @return the number of deleted songs
     */
    public int deletedCount()
    {
        return deletedSongs.size();
    }

    /**
     * This method checks and cleans one typed field. It trims the outside
     * spaces and shrinks extra spaces in the middle down to one.
     *
     * @param text what the user typed
     * @param fieldName the field's name, used in the error message
     * @return the cleaned text
     * @throws IllegalArgumentException throws with a message saying what
     * to fix if the text is blank, too long, or has a tab or line break
     */
    private static String clean(String text, String fieldName)
    {
        if (text == null || text.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Please enter a " + fieldName + ".");
        }
        if (text.contains("\t") || text.contains("\n")
            || text.contains("\r"))
        {
            throw new IllegalArgumentException(
                "Please use plain text, with no tabs or line breaks.");
        }
        String cleaned = text.trim().replaceAll(" +", " ");
        if (cleaned.length() > MAX_LENGTH)
        {
            throw new IllegalArgumentException(
                "Keep it to " + MAX_LENGTH + " characters or fewer.");
        }
        return cleaned;
    }
}