package humm;

/*
 * SKELETON: Song.java
 * Owner: Preston
 *
 * PURPOSE
 *   One song: a title and an artist. It checks its own text, so a bad song
 *   can never exist.
 *
 * PUBLIC API (use exactly these)
 *  - Song(String title, String artist)   - bad text throws
 *    IllegalArgumentException
 *  - String getTitle()    String getArtist()
 *  - boolean equals(Object o)    int hashCode()    String toString()
 *
 * YOUR JOB
 *  - Two private final String fields, title and artist. Nothing changes
 *    after the constructor runs.
 *  - The constructor trims both Strings and checks the title first, then
 *    the artist. A null counts as blank. If the title is bad, throw before
 *    looking at the artist.
 *  - Checks, in this order, with messages written for the user (swap
 *    "title" for "artist" for the other field): blank gives "Please enter a
 *    title."; longer than 80 characters gives "The title must be 80
 *    characters or fewer."; a tab or line break inside gives "The title can
 *    only use plain text, with no tabs or line breaks."
 *  - Store the trimmed text so getTitle() and getArtist() show what the
 *    user typed, minus the outer spaces.
 *  - equals: same class (use getClass(), not instanceof), then title and
 *    artist match when compared lowercase with extra spaces ignored (trim,
 *    and squeeze runs of spaces into one). A different artist means not
 *    equal. null and other types return false.
 *  - hashCode must agree with equals, so build it from the same cleaned
 *    lowercase text.
 *  - toString returns "title by artist".
 *  - No Swing, no LinkedChain, no printing.
 *  - Push this file as soon as it works, right after LinkedChain and before
 *    you write the tests. Tanvi needs it.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - The Songs screen has a title field and an artist field. Add runs new
 *    Song(titleText, artistText) inside try/catch
 *    (IllegalArgumentException) and shows getMessage() next to the fields.
 *    The typed text stays put and nothing is stored. So every message must
 *    be a short, friendly sentence that says what to fix.
 *  - The same Song object goes into the playlist, and the row’s Delete or
 *    Restore button hands that object back. equals decides which copy
 *    leaves, so two identical songs are interchangeable.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - Nothing here waits on anyone.
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
 * Depends on: nothing (it does not use LinkedChain).
 * Tests for this file: SongTest.java.
 */

public class Song
{
    // fields if needed


    /**
     * Constructs a new Song with a given title and artist.
     */
    public Song(String title, String artist)
    {
        // nothing.. for now
    }

    // methods (not implemented yet)

    /**
     * Returns the title of the song.
     */
    public String getTitle()
    {
        return null;
    }

    /**
     * Returns the artist of the song.
     */
    public String getArtist()
    {
        return null;
    }

    /**
     * Returns whether a song is equal to another object.
     */
    @Override
    public boolean equals(Object o)
    {
        return false;
    }

    /**
     * Returns a hash code for the song.
     */
    @Override
    public int hashCode()
    {
        return 0;
    }

    /**
     * Returns a string representation of the song.
     */
    @Override
    public String toString()
    {
        return null;
    }
}