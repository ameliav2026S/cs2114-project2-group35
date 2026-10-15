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
 * The playlists screen: every playlist, with a bird that opens the
 * new-playlist card.
 *
 * @author Amelia Vasquez Rosario
 * @version October 2026
 */
public class PlaylistsPanel
    extends HummWindow.Screen
{
    /** The bird picture in src/main/resources/images. Change the name here. */
    private static final String BIRD = "flower4.GIF";

    private final HummWindow window;
    private final HummWindow.ListPanel list = new HummWindow.ListPanel();
    private final JTextField titleField = HummWindow.field("Late night drive");
    private final JTextField descField =
        HummWindow.field("Songs for the highway");
    private final JLabel message =
        HummWindow.label(" ", HummWindow.body(12f, false), HummWindow.ALERT);
    private final JLabel notice =
        HummWindow.label(" ", HummWindow.body(13f, false), HummWindow.ALERT);
    private final JButton clearAll =
        HummWindow.button("Clear all", HummWindow.ALERT, HummWindow.WHITE);
    private final JButton deleted = HummWindow.button("Deleted playlists",
        HummWindow.TURQUOISE, HummWindow.INK);

    /**
     * Builds the screen.
     *
     * @param window the window this screen lives in
     */
    public PlaylistsPanel(HummWindow window)
    {
        this.window = window;
        addCircle(0, new Color(0xB7FFF0), HummWindow.TURQUOISE);
        addCircle(3, new Color(0xE8D6FF), new Color(0x8355E2));

        JLabel title = HummWindow.label("Tune in",
            HummWindow.heading(30f, false), HummWindow.PURPLE);
        JPanel headRow = new JPanel(new BorderLayout(0, 6));
        headRow.setOpaque(false);
        headRow.add(HummWindow.centeredHeading(title), BorderLayout.CENTER);
        headRow.add(HummWindow.centered(notice), BorderLayout.SOUTH);

        JButton home = HummWindow.button("Home", HummWindow.PURPLE,
            HummWindow.WHITE);
        home.addActionListener(e -> window.showScreen(HummWindow.MAIN));
        clearAll.addActionListener(e -> {
            window.getLibrary().clearPlaylists();
            refresh();
        });
        deleted.addActionListener(
            e -> window.showScreen(HummWindow.DELETED_PLAYLISTS));

        JPanel page = new JPanel(new BorderLayout(0, 20));
        page.add(headRow, BorderLayout.NORTH);
        page.add(HummWindow.scroller(list), BorderLayout.CENTER);
        page.add(HummWindow.buttonRow(home, clearAll, deleted),
            BorderLayout.SOUTH);
        setContent(page);

        JButton cancel = HummWindow.button("Cancel", HummWindow.WHITE,
            HummWindow.PURPLE);
        JButton create = HummWindow.button("Create", HummWindow.PURPLE,
            HummWindow.WHITE);
        cancel.addActionListener(e -> closeCard());
        create.addActionListener(e -> createPlaylist());
        titleField.addActionListener(e -> createPlaylist());
        descField.addActionListener(e -> createPlaylist());
        JPanel card = HummWindow.formCard("New playlist",
            new String[] {"Title", "Description"},
            new JTextField[] {titleField, descField}, message, cancel, create);
        setBird(HummWindow.pictureButton(BIRD,
            "New playlist"), true, card);
    }


    private void closeCard()
    {
        titleField.setText("");
        descField.setText("");
        message.setText(" ");
        setCardOpen(false);
    }


    private void createPlaylist()
    {
        try
        {
            window.getLibrary().createPlaylist(titleField.getText(),
                descField.getText());
        }
        catch (IllegalArgumentException e)
        {
            HummWindow.say(message, e.getMessage());
            return;
        }
        closeCard();
        refresh();
    }


    /** Rebuilds the rows and button labels from the Library. */
    public void refresh()
    {
        closeCard();
        notice.setText(" ");
        List<JPanel> rows = new ArrayList<>();
        Iterator<Playlist> each = window.getLibrary().playlists();
        while (each.hasNext())
        {
            rows.add(makeRow(each.next()));
        }
        HummWindow.fill(list, rows,
            "It’s a little quiet here. Tap the bird to start a playlist.");
        clearAll.setEnabled(window.getLibrary().playlistCount() > 0);
        deleted.setText("Deleted playlists ("
            + window.getLibrary().deletedPlaylistCount() + ")");
    }


    private JPanel makeRow(Playlist playlist)
    {
        int n = playlist.songCount();
        JLabel count = HummWindow.label(n + (n == 1 ? " song" : " songs"),
            HummWindow.body(13f, false), HummWindow.MUTED);
        JButton open = HummWindow.button("Open", HummWindow.PURPLE,
            HummWindow.WHITE);
        JButton delete = HummWindow.button("Delete", HummWindow.ALERT,
            HummWindow.WHITE);
        open.addActionListener(e -> {
            window.setChosenPlaylist(playlist);
            window.showScreen(HummWindow.SONGS);
        });
        delete.addActionListener(e -> {
            try
            {
                window.getLibrary().deletePlaylist(playlist);
                refresh();
            }
            catch (NoSuchElementException ex)
            {
                refresh();
                notice.setText("That item is no longer there.");
            }
        });
        return HummWindow.row(playlist.getTitle(), playlist.getDescription(),
            count, open, delete);
    }
}