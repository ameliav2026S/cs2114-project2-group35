package humm;
import java.util.Iterator;

/**
 * This class holds the apps logic for the playlists.
 * It stores the two LinkedChain<Playlist> chains: one for active playlist and the other for recently deleted playlist.
 *
 * What it does:
 * Creates playlists with a title and description
 * Deletes a playlist by moving it to the Recently Deleted
 * Clears all the playlists there are and moves it to the Recently Deleted at once
 * Empties the Recently Deleted for good
 * Gives the screens iterators and counts so they can draw rows
 * 
 * Library calls LinkedChains to be tested on its own. Library doesn't have Swing code. 
 * Logic: Screens -> Library -> LinkedChain 
 * 
 * @author Tanvi Bhat
 * @version October 15, 2026
 */
public class Library
{
    private final LinkedChain<Playlist> playlists;
    private final LinkedChain<Playlist> deletedPlaylists;

    /**
     * Creates a Library with both chains empty.
     */
    public Library()
    {
        playlists = new LinkedChain<Playlist>();
        deletedPlaylists = new LinkedChain<Playlist>();
    }

    /**
     * Creates a playlist (which checks its own text), adds it to the
     * Library, and returns it.
     *
     * @param title the playlist's title
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
     * This method moves the playlist along with the songs inside of it into Recently Deleted.
     *
     * @param playlist to delete
     * @throws java.util.NoSuchElementException throws if the playlist is not in
     *         the Library
     */
    public void deletePlaylist(Playlist playlist)
    {
        playlists.remove(playlist);
        deletedPlaylists.add(playlist);
    }

    /**
     * This method moves the playlist along with its songs out of Recently Deleted.
     *
     * @param playlist the playlist to restore
     * @throws java.util.NoSuchElementException throws if the playlist is not in
     *         Recently Deleted
     */
    public void restorePlaylist(Playlist playlist)
    {
        deletedPlaylists.remove(playlist);
        playlists.add(playlist);
    }

    /**
     * This method moves all playlists into Recently Deleted, then empties the active
     * chain.
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
     * This method empties the Recently Deleted for good.
     */
    public void emptyDeletedPlaylists()
    {
        deletedPlaylists.clear();
    }

    /**
     * This method (stretch goal) moves all the playlists in Recently Deleted
     * back into the library, then empties Recently Deleted.
     */
    public void restoreAllPlaylists()
    {
        for (Playlist playlist : deletedPlaylists)
        {
            playlists.add(playlist);
        }
        deletedPlaylists.clear();
    }

    /**
     * This method loops over the active playlists to draw rows.
     * This helps the iterator step through a playlist one at a time so other classes can look at each playlist without touching the chain.
     * @return iterator over the playlists
     */
    public Iterator<Playlist> playlists()
    {
        return playlists.iterator();
    }

    /**
     * This method loops over the deleted playlists to draw rows. 
     * This helps the iterator step through a playlist one at a time so other classes can look at each deleted playlist without touching the chain.

     * @return iterator over the deleted playlists
     */
    public Iterator<Playlist> deletedPlaylists()
    {
        return deletedPlaylists.iterator();
    }

    /**
     * This method gets how many playlists are active.
     *
     * @return number of active playlists
     */
    public int playlistCount()
    {
        return playlists.size();
    }

    /**
     * This method gets how many playlists are in Recently Deleted.
     *
     * @return number of deleted playlists
     */
    public int deletedPlaylistCount()
    {
        return deletedPlaylists.size();
    }
}