/*
 * SKELETON: Playlist.java
 * Owner: Tanvi
 *
 * PURPOSE
 *   One playlist: a title, a description, its songs and its own deleted
 *   songs. It is the only class that touches the song chains.
 *
 * PUBLIC API (use exactly these)
 *  - Playlist(String title, String description)   - bad text throws
 *    IllegalArgumentException
 *  - String getTitle()    String getDescription()
 *  - void addSong(Song s)   - null throws IllegalArgumentException
 *  - void deleteSong(Song s)   - songs to deletedSongs; not found throws
 *    NoSuchElementException
 *  - void restoreSong(Song s)   - deletedSongs to songs; not found throws
 *    NoSuchElementException
 *  - void clearSongs()    void emptyDeletedSongs()    (both safe when
 *    empty)
 *  - void restoreAllSongs()   - stretch goal; build it last
 *  - Iterator<Song> songs()    Iterator<Song> deletedSongs()
 *  - int songCount()    int deletedCount()
 *
 * YOUR JOB
 *  - Fields: title and description (Strings), songs and deletedSongs (both
 *    LinkedChain<Song>, made empty in the constructor).
 *  - The constructor trims both Strings and checks title, then description.
 *    A null counts as blank. Each must be 1 to 80 characters with no tab or
 *    line break. Throw IllegalArgumentException with a friendly message
 *    that names the field, like "Please enter a playlist title." or "The
 *    description must be 80 characters or fewer."
 *  - addSong: reject null, then songs.add(s).
 *  - deleteSong: call songs.remove(s) FIRST (it throws if the song is
 *    missing), and only then deletedSongs.add(s). That way a failed delete
 *    changes nothing.
 *  - restoreSong: the same pattern in reverse, deletedSongs.remove(s), then
 *    songs.add(s).
 *  - clearSongs: add every song from songs into deletedSongs, then
 *    songs.clear(). Loop with the iterator and add to the OTHER chain only.
 *  - emptyDeletedSongs: deletedSongs.clear(). restoreAllSongs (stretch):
 *    move everything from deletedSongs back to songs, then clear
 *    deletedSongs.
 *  - songs() and deletedSongs() return the chain’s iterator() (a fresh one
 *    each call). songCount() and deletedCount() return the chain sizes.
 *  - Do NOT override equals or hashCode. Two playlists are equal only if
 *    they are the same object, so deleting one "Getting Ready" can never
 *    hit another.
 *  - Never return or expose a LinkedChain. No Swing, no printing.
 *  - Write this file before Library.java, because Library calls it. Push it
 *    as soon as it works.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - Playlists screen: the Create button goes through
 *    Library.createPlaylist, which calls your constructor. The screen shows
 *    your exception’s message beside the fields, so write messages for the
 *    user.
 *  - Songs screen: shows getTitle() and getDescription(), loops songs() to
 *    draw one row per song, and each row’s Delete button calls deleteSong
 *    with that row’s own Song. After every change the screen redraws all
 *    rows with a fresh iterator.
 *  - "Clear songs" is greyed out when songCount() is 0, and "Empty" on the
 *    Deleted songs screen when deletedCount() is 0, so both counts must
 *    always be exact.
 *  - Deleted songs screen: loops deletedSongs(); each row’s Restore calls
 *    restoreSong.
 *  - If Library deletes this whole playlist, deletedSongs stays inside it.
 *    Restoring the playlist brings everything back with no extra work from
 *    you.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Code against Song and LinkedChain as they are in the skeleton, or use
 *    the provided LinkedChain.java until Preston’s version lands.
 *  - The provided chain’s remove returns false instead of throwing, so the
 *    "not found" tests will fail until his version arrives. Your code
 *    should not need to change.
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
 * Depends on: Song and LinkedChain.
 * Tests for this file: PlaylistTest.java.
 */