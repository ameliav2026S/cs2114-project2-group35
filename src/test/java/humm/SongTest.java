/*
 * SKELETON: SongTest.java
 * Owner: Preston
 *
 * PURPOSE
 *   Proves a Song accepts good text, rejects bad text with a helpful
 *   message, and compares the way the app needs.
 *
 * YOUR JOB
 *  - Constructor: a valid title and artist works. Do the bad cases for BOTH
 *    fields: blank, spaces only, null, 81 characters, a tab inside, a line
 *    break inside. Each throws IllegalArgumentException and the message is
 *    not empty. If both fields are bad, the title message wins.
 *  - Boundary: exactly 80 characters is accepted for each field. Outer
 *    spaces are trimmed, so " Hello " gives getTitle() "Hello".
 *  - equals: "Blinding Lights" by "The Weeknd" and "blinding  lights" by
 *    "the weeknd" (different capitals, extra space) are equal. Same title
 *    with a different artist is not equal. equals(null) and a non-Song are
 *    false.
 *  - hashCode is the same for two equal songs. toString gives "title by
 *    artist".
 *
 * HOW THE SCREENS USE THIS FILE
 *  - The Songs screen shows your exception messages directly, so check each
 *    failure gives a different, useful message.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Nothing here waits on anyone.
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
 * Tests: Song. Test plan, section 8.
 * Tests for this file: tests Song.
 */