/*
 * SKELETON: Library.java
 * Owner: Tanvi
 *
 * PURPOSE
 *   The whole app’s data: the active playlists and the deleted playlists.
 *   Every screen gets its data from here.
 *
 * PUBLIC API (use exactly these)
 *  - Library()
 *  - Playlist createPlaylist(String title, String description)   - bad text
 *    throws IllegalArgumentException
 *  - void deletePlaylist(Playlist p)   - playlists to deletedPlaylists; not
 *    found throws NoSuchElementException
 *  - void restorePlaylist(Playlist p)   - deletedPlaylists to playlists;
 *    not found throws NoSuchElementException
 *  - void clearPlaylists()    void emptyDeletedPlaylists()    (both safe
 *    when empty)
 *  - Iterator<Playlist> playlists()    Iterator<Playlist>
 *    deletedPlaylists()
 *  - int playlistCount()    int deletedPlaylistCount()
 *
 * YOUR JOB
 *  - Fields: playlists and deletedPlaylists (both LinkedChain<Playlist>,
 *    empty at the start).
 *  - createPlaylist: build the Playlist first (its constructor does the
 *    checking), and only if that succeeds add it to playlists and return
 *    it. A bad title or description throws IllegalArgumentException and
 *    nothing is added.
 *  - deletePlaylist: playlists.remove(p) FIRST (it throws if the playlist
 *    is missing), then deletedPlaylists.add(p). A failed delete changes
 *    nothing.
 *  - restorePlaylist: deletedPlaylists.remove(p) first, then
 *    playlists.add(p). Only the Playlist object moves, so its songs and its
 *    own deleted songs come back with it automatically. Do not copy
 *    anything.
 *  - clearPlaylists: add every playlist into deletedPlaylists, then
 *    playlists.clear(). Loop with the iterator and add to the OTHER chain
 *    only. emptyDeletedPlaylists: deletedPlaylists.clear().
 *  - playlists() and deletedPlaylists() return the chain’s iterator()
 *    (fresh each call). The two count methods return the chain sizes.
 *  - Playlists are compared by identity, so p must be the exact object the
 *    screen got from the iterator. You never need a title lookup.
 *  - Never return or expose a LinkedChain. No Swing, no printing.
 *  - Write Playlist.java first, then this one. Push each as soon as it
 *    works.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - HummWindow creates ONE Library when the app starts and every panel
 *    shares it.
 *  - Playlists screen: loops playlists() to draw one row per playlist; each
 *    row’s Delete calls deletePlaylist with that row’s own Playlist. Create
 *    calls createPlaylist and shows your exception’s message if the text is
 *    bad.
 *  - "Clear all" is greyed out when playlistCount() is 0. Deleted playlists
 *    screen loops deletedPlaylists(); Restore calls restorePlaylist;
 *    "Empty" is greyed out when deletedPlaylistCount() is 0. All counts
 *    must always be exact.
 *  - Restoring a playlist must bring back all its songs and its deleted
 *    songs. This works automatically if you move the same object.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Code against Playlist as it is in the skeleton. You never touch Song
 *    directly.
 *  - Starter versions of LinkedChain and Song are in the repo, so the
 *    project compiles. Code and test against what is there. A test that
 *    needs the REAL LinkedChain or Song (a "not found" exception, Song
 *    equality, looping with the iterator) may fail until Preston pushes
 *    his, and that is expected.
 *
 * RULES
 *  - Package humm. Keep every class, method, parameter and return type
 *    exactly as listed in PUBLIC API and in the spec (section 6). Do not
 *    add, rename or remove public methods, and do not add public fields.
 *    Helpers must be private.
 *  - Pull the repo before you start and pull again often. LinkedChain.java
 *    and Song.java are already in it as starter files; replace their
 *    placeholder bodies and keep their headers. For any other file of
 *    yours, first write the class and method headers from PUBLIC API with
 *    placeholder bodies, so it compiles, and then fill in the real code.
 *  - Everything is due Tuesday night, so Amelia can put it together on
 *    Wednesday.
 *  - Write a Javadoc comment on the class and on every public method,
 *    directly above the method, with @param, @return and @throws where they
 *    apply. It keeps the code easy to explain in the oral exam.
 *  - Exceptions: use only the ones named here, and always give them a short
 *    message. Never return null to signal an error, never catch an
 *    exception just to hide it, and never print to the console.
 *  - A failed action must leave everything unchanged. Check first, then
 *    change.
 *  - Edit only your own files. If you find a problem in someone else’s
 *    file, or need a signature changed, tell the group and change the spec
 *    first.
 *  - Delete this comment block before submitting.
 *
 * Depends on: Playlist and LinkedChain.
 * Tests for this file: LibraryTest.java.
 */