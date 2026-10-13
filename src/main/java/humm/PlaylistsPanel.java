/*
 * SKELETON: PlaylistsPanel.java
 * Owner: Amelia
 *
 * PURPOSE
 *   Screen 2, all active playlists.
 *
 * PUBLIC API (use exactly these)
 *  - PlaylistsPanel(HummWindow window)    void refresh()
 *
 * YOUR JOB
 *  - One row per playlist: loop window.getLibrary().playlists(). Each row
 *    shows the title and description, has its own Delete button, and a way
 *    to open it (set window.setChosenPlaylist(thatPlaylist), then
 *    showScreen("SONGS")).
 *  - In each row, copy the Playlist into a final variable and use THAT in
 *    the button handler. This is what makes Delete hit the right row even
 *    when titles repeat.
 *  - Create: title and description fields and a Create button calling
 *    library.createPlaylist(...). Catch IllegalArgumentException, show
 *    getMessage() in a label near the fields, keep what the user typed, and
 *    add nothing.
 *  - Buttons: Clear all (library.clearPlaylists()) and Deleted playlists
 *    (showScreen("DELETED_PLAYLISTS")).
 *  - If a Delete throws NoSuchElementException, show "That item is no
 *    longer there." and refresh.
 *  - refresh(): remove all rows, rebuild them from a fresh iterator, grey
 *    out Clear all when playlistCount() is 0, clear the message label. When
 *    empty, show "It’s a little quiet here. Create a playlist to get
 *    started." instead of rows.
 *  - Call refresh() after every successful change. Never change the library
 *    while looping over an iterator: collect the click first, then act.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Use the skeleton Library. The real one fills in later with the same
 *    signatures.
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
 * Depends on: Library, Playlist, HummWindow.
 * Tests for this file: by-hand checklist.
 */