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
 * The recently deleted playlists, with a way to bring them back.
 *
 * @author Amelia Vasquez Rosario
 * @version October 2026
 */
public class DeletedPlaylistsPanel
    extends HummWindow.Screen
{
    private final HummWindow window;
    private final HummWindow.ListPanel list = new HummWindow.ListPanel();
    private final JLabel notice =
        HummWindow.label(" ", HummWindow.body(13f, false), HummWindow.ALERT);
    private final JButton emptyForGood = HummWindow.button("Empty for good",
        HummWindow.WHITE, HummWindow.ALERT);

    /**
     * Builds the screen.
     *
     * @param window the window this screen lives in
     */
    public DeletedPlaylistsPanel(HummWindow window)
    {
        this.window = window;
        JButton back = HummWindow.button("Back to playlists",
            HummWindow.WHITE, HummWindow.PURPLE);
        back.addActionListener(e -> window.showScreen(HummWindow.PLAYLISTS));
        emptyForGood.addActionListener(e -> {
            window.getLibrary().emptyDeletedPlaylists();
            refresh();
        });

        JLabel title = HummWindow.label("Recently deleted playlists",
            HummWindow.heading(30f, false), HummWindow.PURPLE);
        JPanel top = new JPanel(new BorderLayout(0, 20));
        top.setOpaque(false);
        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backRow.setOpaque(false);
        backRow.add(back);
        top.add(backRow, BorderLayout.NORTH);
        JPanel headRow = new JPanel(new BorderLayout());
        headRow.setOpaque(false);
        headRow.add(HummWindow.heading(title, null, false),
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


    /** Rebuilds the rows from the Library's deleted playlists. */
    public void refresh()
    {
        notice.setText(" ");
        List<JPanel> rows = new ArrayList<>();
        Iterator<Playlist> each = window.getLibrary().deletedPlaylists();
        while (each.hasNext())
        {
            rows.add(makeRow(each.next()));
        }
        HummWindow.fill(list, rows, "Nothing here. Deleted playlists show up "
            + "here so you can restore them.");
        emptyForGood.setEnabled(window.getLibrary().deletedPlaylistCount() > 0);
    }


    private JPanel makeRow(Playlist playlist)
    {
        int n = playlist.songCount();
        JLabel count = HummWindow.label(n + (n == 1 ? " song" : " songs"),
            HummWindow.body(13f, false), HummWindow.MUTED);
        JButton restore = HummWindow.button("Restore", HummWindow.TURQUOISE,
            HummWindow.INK);
        restore.addActionListener(e -> {
            try
            {
                window.getLibrary().restorePlaylist(playlist);
                refresh();
            }
            catch (NoSuchElementException ex)
            {
                refresh();
                notice.setText("That item is no longer there.");
            }
        });
        return HummWindow.row(playlist.getTitle(), playlist.getDescription(),
            count, restore);
    }
}