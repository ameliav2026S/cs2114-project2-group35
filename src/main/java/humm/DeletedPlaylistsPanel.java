/*
 * SKELETON: DeletedPlaylistsPanel.java
 * Owner: Amelia
 *
 * PURPOSE
 *   Screen 5, Recently Deleted playlists.
 *
 * PUBLIC API (use exactly these)
 *  - DeletedPlaylistsPanel(HummWindow window)    void refresh()
 *
 * YOUR JOB
 *  - One row per playlist from library.deletedPlaylists(), each with its
 *    own Restore button (final copy of that row’s Playlist, then
 *    library.restorePlaylist(p)).
 *  - Buttons: Empty (library.emptyDeletedPlaylists(), gone for good) and
 *    Back (showScreen("PLAYLISTS")). Grey out Empty when
 *    deletedPlaylistCount() is 0.
 *  - If a restore throws NoSuchElementException, show "That item is no
 *    longer there." and refresh.
 *  - refresh(): rebuild rows from a fresh iterator. When empty, show
 *    "Nothing here. Deleted playlists show up here so you can restore
 *    them."
 *  - Restoring brings the playlist back with all its songs. Nothing extra
 *    to do on this screen.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Use the skeleton Library.
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
 * Depends on: Library, HummWindow.
 * Tests for this file: by-hand checklist.
 */