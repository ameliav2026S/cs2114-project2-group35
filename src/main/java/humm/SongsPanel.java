/*
 * SKELETON: SongsPanel.java
 * Owner: Amelia
 *
 * PURPOSE
 *   Screen 3, the songs in the open playlist.
 *
 * PUBLIC API (use exactly these)
 *  - SongsPanel(HummWindow window)    void refresh()
 *
 * YOUR JOB
 *  - Get the playlist from window.getChosenPlaylist(). Show its title and
 *    description, then one row per song from playlist.songs(). Each row
 *    shows "title by artist" and has its own Delete button, using a final
 *    copy of that row’s Song in the handler.
 *  - Add: title and artist fields and an Add button doing
 *    playlist.addSong(new Song(...)). Catch IllegalArgumentException, show
 *    getMessage() near the fields, keep the typed text, add nothing.
 *  - Buttons: Clear songs (playlist.clearSongs()), Deleted songs
 *    (showScreen("DELETED_SONGS")), Back (showScreen("PLAYLISTS")).
 *  - If deleteSong throws NoSuchElementException, show "That item is no
 *    longer there." and refresh.
 *  - refresh(): rebuild rows from a fresh iterator, grey out Clear songs
 *    when songCount() is 0, clear the message label. When empty, show "No
 *    songs yet. Add one above."
 *  - If getChosenPlaylist() is null (should not happen), go back to the
 *    playlists screen instead of crashing.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Use the skeleton Playlist. Same signatures later.
 *  - Every other class already exists as a skeleton with the right
 *    signatures, so the project compiles. Code and test against them.
 *
 * RULES
 *  - Package humm. Keep every class, method, parameter and return type
 *    exactly as listed in PUBLIC API and in the spec (section 6). Do not
 *    add, rename or remove public methods, and do not add public fields.
 *    Helpers must be private.
 *  - The skeleton already has the headers. Replace the placeholder bodies
 *    and keep the headers as they are.
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
 * Depends on: Playlist, HummWindow (for the open playlist).
 * Tests for this file: by-hand checklist.
 */