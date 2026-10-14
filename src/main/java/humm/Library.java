package humm;

import java.util.Iterator;

/**
 * The app's logic: every playlist, plus the deleted playlists in
 * Recently Deleted. Library has no Swing in it, so it can be tested on
 * its own.
 *
 * A deleted playlist keeps its songs and its own deleted songs, so
 * restoring it brings everything back at once.
 *
 * @author Tanvi Bhat
 * @version October 2026
 */
public class Library
{
    private final LinkedChain<Playlist> playlists;
    private final LinkedChain<Playlist> deletedPlaylists;

    /**
     * Makes a Library with both chains empty.
     */
    public Library()
    {
        playlists = new LinkedChain<Playlist>();
        deletedPlaylists = new LinkedChain<Playlist>();
    }

    /**
     * Makes a playlist (which checks its own text), adds it to the
     * Library, and returns it.
     *
     * @param title       the playlist's title
     * @param description the playlist's description
     * @return the new playlist
     * @throws IllegalArgumentException if the title or description is bad
     */
    public Playlist createPlaylist(String title, String description)
    {
        Playlist playlist = new Playlist(title, description);
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Moves that exact playlist into Recently Deleted. Its songs go
     * with it.
     *
     * @param playlist the playlist to delete
     * @throws java.util.NoSuchElementException if the playlist is not in
     *         the Library
     */
    public void deletePlaylist(Playlist playlist)
    {
        playlists.remove(playlist);
        deletedPlaylists.add(playlist);
    }

    /**
     * Moves that exact playlist back out of Recently Deleted, with all of
     * its songs and its own deleted songs.
     *
     * @param playlist the playlist to restore
     * @throws java.util.NoSuchElementException if the playlist is not in
     *         Recently Deleted
     */
    public void restorePlaylist(Playlist playlist)
    {
        deletedPlaylists.remove(playlist);
        playlists.add(playlist);
    }

    /**
     * Moves every playlist into Recently Deleted, then empties the active
     * chain. Safe when there are no playlists.
     */
    public void clearPlaylists()
    {
        for (Playlist playlist : playlists)
        {
            deletedPlaylists.add(playlist);
        }
        playlists.clear();
    }

    /**
     * Empties Recently Deleted for good. Safe when it is already empty.
     */
    public void emptyDeletedPlaylists()
    {
        deletedPlaylists.clear();
    }

    /**
     * Loops over the active playlists, for drawing rows. Order is not
     * promised.
     *
     * @return an iterator over the playlists
     */
    public Iterator<Playlist> playlists()
    {
        return playlists.iterator();
    }

    /**
     * Loops over the deleted playlists, for drawing rows. Order is not
     * promised.
     *
     * @return an iterator over the deleted playlists
     */
    public Iterator<Playlist> deletedPlaylists()
    {
        return deletedPlaylists.iterator();
    }

    /**
     * Gets how many playlists are active.
     *
     * @return the number of active playlists
     */
    public int playlistCount()
    {
        return playlists.size();
    }

    /**
     * Gets how many playlists are in Recently Deleted.
     *
     * @return the number of deleted playlists
     */
    public int deletedPlaylistCount()
    {
        return deletedPlaylists.size();
    }
}