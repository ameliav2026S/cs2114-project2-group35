package humm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The songs screen for the open playlist, with a bird that opens the
 * add-a-song card.
 *
 * @author Amelia Vasquez Rosario
 * @version October 2026
 */
public class SongsPanel
    extends HummWindow.Screen
{
    private final HummWindow window;
    private final HummWindow.ListPanel list = new HummWindow.ListPanel();
    private final JLabel title = HummWindow.label(" ",
        HummWindow.heading(30f, false), HummWindow.PURPLE);
    private final JLabel subtitle = HummWindow.label(" ",
        HummWindow.body(15f, false), HummWindow.MUTED);
    private final JTextField titleField = HummWindow.field("Clair de Lune");
    private final JTextField artistField = HummWindow.field("Debussy");
    private final JLabel message =
        HummWindow.label(" ", HummWindow.body(12f, false), HummWindow.ALERT);
    private final JLabel notice =
        HummWindow.label(" ", HummWindow.body(13f, false), HummWindow.ALERT);
    private final JButton clearSongs = HummWindow.button("Clear songs",
        HummWindow.WHITE, HummWindow.ALERT);
    private final JButton deleted = HummWindow.button("Deleted songs",
        HummWindow.TURQUOISE, HummWindow.INK);

    /**
     * Builds the screen.
     *
     * @param window the window this screen lives in
     */
    public SongsPanel(HummWindow window)
    {
        this.window = window;
        addCircle(1, new Color(0xD9FBF7), HummWindow.TURQUOISE);
        addCircle(2, new Color(0xDDC9F9), new Color(0x9562FF));

        JPanel headRow = new JPanel(new BorderLayout());
        headRow.setOpaque(false);
        headRow.add(HummWindow.heading(title, subtitle, false),
            BorderLayout.WEST);
        headRow.add(notice, BorderLayout.EAST);

        JButton back = HummWindow.button("Back to playlists",
            HummWindow.WHITE, HummWindow.PURPLE);
        back.addActionListener(e -> window.showScreen(HummWindow.PLAYLISTS));
        clearSongs.addActionListener(e -> {
            Playlist playlist = window.getChosenPlaylist();
            if (playlist != null)
            {
                playlist.clearSongs();
            }
            refresh();
        });
        deleted.addActionListener(
            e -> window.showScreen(HummWindow.DELETED_SONGS));

        JPanel page = new JPanel(new BorderLayout(0, 20));
        page.add(headRow, BorderLayout.NORTH);
        page.add(HummWindow.scroller(list), BorderLayout.CENTER);
        page.add(HummWindow.buttonRow(back, clearSongs, deleted),
            BorderLayout.SOUTH);
        setContent(page);

        JButton cancel = HummWindow.button("Cancel", HummWindow.WHITE,
            HummWindow.PURPLE);
        JButton add = HummWindow.button("Add song", HummWindow.PURPLE,
            HummWindow.WHITE);
        cancel.addActionListener(e -> closeCard());
        add.addActionListener(e -> addSong());
        titleField.addActionListener(e -> addSong());
        artistField.addActionListener(e -> addSong());
        JPanel card = HummWindow.formCard("Add a song",
            new String[] {"Song title", "Artist"},
            new JTextField[] {titleField, artistField}, message, cancel, add);
        setBird(HummWindow.pictureButton("vinyl.GIF", "Add a song"),
            false, card);
    }


    private void closeCard()
    {
        titleField.setText("");
        artistField.setText("");
        message.setText(" ");
        setCardOpen(false);
    }


    private void addSong()
    {
        Playlist playlist = window.getChosenPlaylist();
        if (playlist == null)
        {
            return;
        }
        try
        {
            playlist.addSong(new Song(titleField.getText(),
                artistField.getText()));
        }
        catch (IllegalArgumentException e)
        {
            message.setText(e.getMessage());
            return;
        }
        closeCard();
        refresh();
    }


    /** Rebuilds the rows and button labels from the open playlist. */
    public void refresh()
    {
        closeCard();
        notice.setText(" ");
        Playlist playlist = window.getChosenPlaylist();
        if (playlist == null)
        {
            return;
        }
        title.setText(playlist.getTitle());
        title.setToolTipText(playlist.getTitle());
        String description = playlist.getDescription();
        subtitle.setText(description.isEmpty() ? " " : description);
        List<JPanel> rows = new ArrayList<>();
        Iterator<Song> each = playlist.songs();
        while (each.hasNext())
        {
            rows.add(makeRow(playlist, each.next()));
        }
        HummWindow.fill(list, rows, "No songs yet. Tap the bird to add one.");
        clearSongs.setEnabled(playlist.songCount() > 0);
        deleted.setText("Deleted songs (" + playlist.deletedCount() + ")");
    }


    private JPanel makeRow(Playlist playlist, Song song)
    {
        JButton delete = HummWindow.button("Delete", HummWindow.WHITE,
            HummWindow.ALERT);
        delete.addActionListener(e -> {
            try
            {
                playlist.deleteSong(song);
                refresh();
            }
            catch (NoSuchElementException ex)
            {
                refresh();
                notice.setText("That item is no longer there.");
            }
        });
        return HummWindow.row(song.getTitle(), song.getArtist(), delete);
    }
}