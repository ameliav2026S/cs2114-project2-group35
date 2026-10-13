/*
 * SKELETON: HummWindow.java
 * Owner: Amelia
 *
 * PURPOSE
 *   The app’s window and entry point. It owns the one Library, remembers
 *   which playlist is open, and switches between the five screens.
 *
 * PUBLIC API (use exactly these)
 *  - HummWindow(Library lib)
 *  - public static void main(String[] args)
 *  - void showScreen(String name)   - names: "MAIN", "PLAYLISTS", "SONGS",
 *    "DELETED_SONGS", "DELETED_PLAYLISTS"
 *  - Library getLibrary()
 *  - Playlist getChosenPlaylist()    void setChosenPlaylist(Playlist p)   -
 *    null when none is open
 *
 * YOUR JOB
 *  - A JFrame whose content uses a CardLayout, one card per panel. Build
 *    all five panels once, in the constructor.
 *  - main sets up FlatLaf BEFORE any Swing component exists, then opens the
 *    window with SwingUtilities.invokeLater.
 *  - showScreen(name) refreshes that panel, then switches to it, so it
 *    always shows current data.
 *  - Add the three small getters and setter above to the spec, section 6.
 *  - The window and panels talk only to Library and Playlist. They never
 *    touch a LinkedChain.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Build with the skeleton versions of Library and Playlist; they compile
 *    with the right signatures.
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
 * Depends on: Library, Playlist and the five panels.
 * Tests for this file: by-hand checklist.
 */