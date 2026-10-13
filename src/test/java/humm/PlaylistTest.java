/*
 * SKELETON: PlaylistTest.java
 * Owner: Tanvi
 *
 * PURPOSE
 *   Proves a Playlist keeps its songs and deleted songs correctly, and that
 *   a failed action changes nothing.
 *
 * YOUR JOB
 *  - Constructor: valid text makes a playlist with songCount 0 and
 *    deletedCount 0, and getTitle and getDescription return the trimmed
 *    text. Blank title, blank description, null, a tab inside, or over 80
 *    characters (title and description each) throws
 *    IllegalArgumentException with a message. Exactly 80 is accepted.
 *  - addSong: add 2 songs, songCount is 2. Adding null throws and the count
 *    stays the same.
 *  - deleteSong: delete one of 2, so songCount 1 and deletedCount 1.
 *    Deleting a song that is not in the playlist throws
 *    NoSuchElementException and BOTH counts stay the same.
 *  - restoreSong: a deleted song is back in songs and gone from
 *    deletedSongs. Restoring a song that is not deleted throws
 *    NoSuchElementException and both counts stay the same.
 *  - clearSongs: 3 songs gives songCount 0 and deletedCount 3.
 *    emptyDeletedSongs: deletedCount 0. Both are fine on an empty playlist.
 *  - Iterators: after 2 adds and 1 delete, looping songs() visits 1 and
 *    deletedSongs() visits 1. A new playlist visits none.
 *  - Duplicates: add the same song twice, delete once, and one copy remains
 *    in songs while deletedCount is 1.
 *  - Round trips: delete a song then restore it, and the counts are back to
 *    the start. Clear 3 songs then restore one: songCount 1, deletedCount
 *    2.
 *  - Failed actions change nothing: after every exception test, check BOTH
 *    songCount and deletedCount.
 *  - Identity: two playlists both called "Getting Ready"; changing one
 *    leaves the other untouched.
 *  - Add a restoreAllSongs test only if you build the stretch goal.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - The screens grey out buttons using songCount() and deletedCount(), so
 *    check the counts after every operation.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Test against the skeleton Song and chain. Re-run when Preston’s
 *    versions land.
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
 * Tests: Playlist. Test plan, section 8.
 * Tests for this file: tests Playlist.
 */