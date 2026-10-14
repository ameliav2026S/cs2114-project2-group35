package humm;

import java.util.Iterator;

/**
 * One playlist in Humm: a title, a description, and two chains of songs.
 * The songs chain holds what is in the playlist now; the deletedSongs
 * chain is this playlist's Recently Deleted.
 *
 * Playlist does NOT override equals(), on purpose. Two playlists are
 * equal only if they are the same object, so deleting one playlist never
 * removes a different playlist that happens to have the same title.
 *
 * @author Tanvi Bhat
 * @version October 2026
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
     * Makes a playlist with two empty chains after checking its fields.
     *
     * @param title       the playlist's title
     * @param description the playlist's description
     * @throws IllegalArgumentException if either field is blank, longer
     *         than 80 characters, or contains a tab or line break
     */
    public Playlist(String title, String description)
    {
        this.title = clean(title, "title");
        this.description = clean(description, "description");
        songs = new LinkedChain<Song>();
        deletedSongs = new LinkedChain<Song>();
    }

    /**
     * Gets the title.
     *
     * @return the cleaned title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the description.
     *
     * @return the cleaned description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Adds a song to this playlist. Duplicates are allowed.
     *
     * @param song the song to add
     * @throws IllegalArgumentException if song is null
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
     * Moves one matching song from songs into deletedSongs.
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
     * Moves one matching song from deletedSongs back into songs.
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
     * Moves every song into deletedSongs, then empties songs.
     * Safe on an empty playlist.
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
     * Empties Recently Deleted for good. Safe when it is already empty.
     */
    public void emptyDeletedSongs()
    {
        deletedSongs.clear();
    }

    /**
     * Stretch goal: moves every deleted song back into songs, then
     * empties Recently Deleted. Safe when it is already empty.
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
     * Loops over the songs currently in the playlist, for drawing rows.
     * Order is not promised.
     *
     * @return an iterator over the songs
     */
    public Iterator<Song> songs()
    {
        return songs.iterator();
    }

    /**
     * Loops over this playlist's Recently Deleted songs, for drawing rows.
     * Order is not promised.
     *
     * @return an iterator over the deleted songs
     */
    public Iterator<Song> deletedSongs()
    {
        return deletedSongs.iterator();
    }

    /**
     * Gets how many songs are in the playlist.
     *
     * @return the number of songs
     */
    public int songCount()
    {
        return songs.size();
    }

    /**
     * Gets how many songs are in Recently Deleted.
     *
     * @return the number of deleted songs
     */
    public int deletedCount()
    {
        return deletedSongs.size();
    }

    /**
     * Checks and cleans one typed field.
     *
     * @param text      what the user typed
     * @param fieldName the field's name, used in the error message
     * @return the trimmed text, with runs of spaces shrunk to one space
     * @throws IllegalArgumentException with a message saying what to fix
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