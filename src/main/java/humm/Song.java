
package humm;

import java.util.Locale;
import java.util.Objects;

/**
 * Immutable song title and artist with user-friendly validation. Equality
 * ignores capitalization and repeated ordinary spaces.
 *
 * @author Preston Moore
 * @version October 2026
 */
public class Song
{
    private final String title;
    private final String artist;

    /**
     * Constructs a song after validating its fields, title first.
     * 
     * @param title
     *            song title
     * @param artist
     *            artist name
     * @throws IllegalArgumentException
     *             if either field is invalid
     */
    public Song(String title, String artist)
    {
        this.title = validate(title, "title");
        this.artist = validate(artist, "artist");
    }


    /**
     * Checks a field and returns its trimmed form.
     * 
     * @param value
     *            entered text
     * @param field
     *            field name for error messages
     * @return trimmed text
     * @throws IllegalArgumentException
     *             if invalid
     */
    private static String validate(String value, String field)
    {
        if (value == null || value.trim().isEmpty())
        {
    String article = "aeiou".indexOf(field.charAt(0)) >= 0 ? "an" : "a";
    throw new IllegalArgumentException(
        "Please enter " + article + " " + field + ".");
        }
        
        String trimmed = value.trim();
        if (trimmed.length() > 80)
        {
            throw new IllegalArgumentException(
                "The " + field + " must be 80 characters or fewer.");
        }
        if (trimmed.indexOf('\t') >= 0 || trimmed.indexOf('\n') >= 0
            || trimmed.indexOf('\r') >= 0)
        {
            throw new IllegalArgumentException(
                "The " + field
                    + " can only use plain text, with no tabs or line breaks.");
        }
        return trimmed;
    }


    /** @return song title, with outer spaces removed */
    public String getTitle()
    {
        return title;
    }


    /** @return artist name, with outer spaces removed */
    public String getArtist()
    {
        return artist;
    }


    /**
     * Normalizes case and runs of ordinary spaces for comparisons.
     * 
     * @param value
     *            validated text
     * @return normalized text
     */
    private static String normalized(String value)
    {
        return value.trim().replaceAll(" +", " ").toLowerCase(Locale.ROOT);
    }


    /**
     * Compares songs by normalized title and artist.
     * 
     * @param other
     *            object to compare
     * @return true if other is a Song with matching title and artist
     */
    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (other == null || getClass() != other.getClass())
        {
            return false;
        }
        Song song = (Song)other;
        return normalized(title).equals(normalized(song.title))
            && normalized(artist).equals(normalized(song.artist));
    }


    /** @return hash code consistent with equals */
    @Override
    public int hashCode()
    {
        return Objects.hash(normalized(title), normalized(artist));
    }


    /** @return readable "title by artist" description */
    @Override
    public String toString()
    {
        return title + " by " + artist;
    }
}
