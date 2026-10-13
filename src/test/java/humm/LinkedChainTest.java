/*
 * SKELETON: LinkedChainTest.java
 * Owner: Preston
 *
 * PURPOSE
 *   Proves the chain works for any item type, including the failure cases
 *   the screens rely on.
 *
 * YOUR JOB
 *  - One normal and one bad-input test per public method, from the test
 *    plan. New chain: size 0 and isEmpty. Add two: size 2. add(null) throws
 *    IllegalArgumentException and size is unchanged. Add "A" twice: size 2
 *    and count("A") is 2.
 *  - remove: A, B, C then remove B leaves A and C. Also remove the FIRST
 *    item and the LAST item of a chain (head and tail are where
 *    linked-chain bugs hide). Remove from an empty chain, a missing item,
 *    or null each throw NoSuchElementException, and size does not change.
 *    With two equal items only one goes.
 *  - contains: present is true; missing, empty or null is false. size and
 *    isEmpty after 3 adds and 1 remove. count, and toString ("[]" when
 *    empty).
 *  - iterator: 3 items visit each exactly once (count the visits, do not
 *    rely on order); empty visits none; next() past the end throws
 *    NoSuchElementException; a new iterator after a remove shows the new
 *    contents.
 *  - clear: full becomes size 0; already empty is fine.
 *  - Reuse proof: one test that fills a LinkedChain<Song> and a
 *    LinkedChain<Playlist> in the same method and checks both, then repeat
 *    add, remove and contains with Song as the item type.
 *  - Make sure every branch is hit, including each throw line. Your IDE’s
 *    "run with coverage" shows what the tests missed.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - Playlist and Library depend on these behaviours, so test them hard:
 *    remove throws when it should and changes nothing when it does, and
 *    size is always right.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Use small test types such as String until Song exists.
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
 * Tests: LinkedChain. Test plan, section 8.
 * Tests for this file: tests LinkedChain.
 */