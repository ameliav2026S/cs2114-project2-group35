/*
 * SKELETON: LibraryTest.java
 * Owner: Tanvi
 *
 * PURPOSE
 *   Proves the Library manages playlists correctly, including that deleting
 *   and restoring a playlist keeps its songs.
 *
 * YOUR JOB
 *  - createPlaylist: valid text raises playlistCount by 1 and returns the
 *    new playlist. A blank title, a blank description, or text over 80
 *    characters throws IllegalArgumentException and the count is unchanged.
 *  - deletePlaylist: a playlist with 3 songs leaves playlists and appears
 *    in deletedPlaylists (counts 0 and 1). Deleting one that is not in the
 *    Library throws NoSuchElementException and both counts stay the same.
 *  - restorePlaylist: it comes back with all 3 songs, and its own deleted
 *    songs are still there. Restoring one that is not deleted throws
 *    NoSuchElementException.
 *  - clearPlaylists: 2 playlists gives active count 0 and deleted count 2.
 *    emptyDeletedPlaylists: deleted count 0. Both are fine on empty chains.
 *  - Iterators: after 2 creates and 1 delete, looping playlists() visits 1
 *    and deletedPlaylists() visits 1. A new Library visits none.
 *  - Identity: create two playlists with the same title, delete one, and
 *    the other is still active.
 *  - Round trip: add 3 songs to a playlist, delete a song inside it, delete
 *    the playlist, restore the playlist, and check the 2 songs and 1
 *    deleted song are all still there.
 *  - Failed actions change nothing: after every exception test, check BOTH
 *    counts.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - The screens grey out buttons using the counts, so check them after
 *    every operation.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Test against the skeleton Playlist. Re-run when the real classes land.
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
 *  - Tests use JUnit 5 (org.junit.jupiter.api.Test, Assertions.assertThrows
 *    and friends, @BeforeEach), matching pom.xml. These are plain classes;
 *    do not extend anything. Make fresh objects in @BeforeEach so tests
 *    never depend on each other. No Swing in tests, and never depend on the
 *    order of items in a chain.
 *
 * Tests: Library. Test plan, section 8.
 * Tests for this file: tests Library.
 */