package humm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The open playlist's recently deleted songs, with a way to bring them back.
 *
 * @author Amelia Vasquez Rosario
 * @version October 2026
 */
public class DeletedSongsPanel
    extends HummWindow.Screen
{
    private final HummWindow window;
    private final HummWindow.ListPanel list = new HummWindow.ListPanel();
    private final JButton back = HummWindow.button("Back",
        HummWindow.WHITE, HummWindow.PURPLE);
    private final JLabel subtitle = HummWindow.label(" ",
        HummWindow.body(15f, false), HummWindow.MUTED);
    private final JLabel notice =
        HummWindow.label(" ", HummWindow.body(13f, false), HummWindow.ALERT);
    private final JButton emptyForGood = HummWindow.button("Empty for good",
        HummWindow.WHITE, HummWindow.ALERT);

    /**
     * Builds the screen.
     *
     * @param window the window this screen lives in
     */
    public DeletedSongsPanel(HummWindow window)
    {
        this.window = window;
        back.addActionListener(e -> window.showScreen(HummWindow.SONGS));
        emptyForGood.addActionListener(e -> {
            Playlist playlist = window.getChosenPlaylist();
            if (playlist != null)
            {
                playlist.emptyDeletedSongs();
            }
            refresh();
        });

        JLabel title = HummWindow.label("Recently deleted songs",
            HummWindow.heading(30f, false), HummWindow.PURPLE);
        JPanel top = new JPanel(new BorderLayout(0, 20));
        top.setOpaque(false);
        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backRow.setOpaque(false);
        backRow.add(back);
        top.add(backRow, BorderLayout.NORTH);
        JPanel headRow = new JPanel(new BorderLayout());
        headRow.setOpaque(false);
        headRow.add(HummWindow.heading(title, subtitle, false),
            BorderLayout.WEST);
        headRow.add(notice, BorderLayout.EAST);
        top.add(headRow, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottom.setOpaque(false);
        bottom.add(emptyForGood);

        JPanel page = new JPanel(new BorderLayout(0, 20));
        page.add(top, BorderLayout.NORTH);
        page.add(HummWindow.scroller(list), BorderLayout.CENTER);
        page.add(bottom, BorderLayout.SOUTH);
        setContent(page);
    }


    /** Rebuilds the rows from the open playlist's deleted songs. */
    public void refresh()
    {
        notice.setText(" ");
        Playlist playlist = window.getChosenPlaylist();
        if (playlist == null)
        {
            return;
        }
        back.setText("Back to " + playlist.getTitle());
        subtitle.setText("from " + playlist.getTitle());
        List<JPanel> rows = new ArrayList<>();
        Iterator<Song> each = playlist.deletedSongs();
        while (each.hasNext())
        {
            rows.add(makeRow(playlist, each.next()));
        }
        HummWindow.fill(list, rows,
            "Nothing here. Deleted songs show up here so you can restore them.");
        emptyForGood.setEnabled(playlist.deletedCount() > 0);
    }


    private JPanel makeRow(Playlist playlist, Song song)
    {
        JButton restore = HummWindow.button("Restore", HummWindow.TURQUOISE,
            HummWindow.INK);
        restore.addActionListener(e -> {
            try
            {
                playlist.restoreSong(song);
                refresh();
            }
            catch (NoSuchElementException ex)
            {
                refresh();
                notice.setText("That item is no longer there.");
            }
        });
        return HummWindow.row(song.getTitle(), song.getArtist(), restore);
    }
}