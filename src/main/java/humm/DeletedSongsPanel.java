/*
 * SKELETON: DeletedSongsPanel.java
 * Owner: Amelia
 *
 * PURPOSE
 *   Screen 4, Recently Deleted songs for the open playlist only.
 *
 * PUBLIC API (use exactly these)
 *  - DeletedSongsPanel(HummWindow window)    void refresh()
 *
 * YOUR JOB
 *  - One row per song from window.getChosenPlaylist().deletedSongs(), each
 *    with its own Restore button (final copy of that row’s Song, then
 *    playlist.restoreSong(song)).
 *  - Buttons: Empty (playlist.emptyDeletedSongs(), gone for good) and Back
 *    (showScreen("SONGS")). Grey out Empty when deletedCount() is 0.
 *  - If a restore throws NoSuchElementException, show "That item is no
 *    longer there." and refresh.
 *  - refresh(): rebuild rows from a fresh iterator. When empty, show
 *    "Nothing here. Deleted songs show up here so you can restore them."
 *  - Restore all is a stretch goal. Add it only after everything else
 *    works.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Use the skeleton Playlist.
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
 * Depends on: Playlist, HummWindow.
 * Tests for this file: by-hand checklist.
 */