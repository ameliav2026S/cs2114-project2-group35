package humm;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * The one window. It holds the Library, remembers which playlist is open,
 * and swaps between the five screens. It also holds the small builders
 * (colors, fonts, buttons) that every screen shares, so each screen file
 * only has to say what is on it.
 *
 * @author Amelia Vasquez Rosario
 * @version October 2026
 */
public class HummWindow
    extends JFrame
{
    // ---- screen names ----

    /** The main screen. */
    public static final String MAIN = "main";
    /** The playlists screen. */
    public static final String PLAYLISTS = "playlists";
    /** The songs screen for the open playlist. */
    public static final String SONGS = "songs";
    /** The open playlist's recently deleted songs. */
    public static final String DELETED_SONGS = "deletedSongs";
    /** The recently deleted playlists. */
    public static final String DELETED_PLAYLISTS = "deletedPlaylists";

    // ---- colors ----

    static final Color PURPLE = new Color(0x5B2A9E);
    static final Color INK = new Color(0x1E1033);
    static final Color MUTED = new Color(0x5D4F7C);
    static final Color LAVENDER = new Color(0xF1ECFF);
    static final Color LINE = new Color(0xE1D8F3);
    static final Color TURQUOISE = new Color(0x3EE0D0);
    static final Color BACKGROUND = new Color(0xFAF8FF);
    static final Color ALERT = new Color(0xB3261E);
    static final Color WHITE = Color.WHITE;

    // ---- fonts (loaded once from src/main/resources/fonts) ----

    private static final Font COMFORTAA = loadFont("Comfortaa-Regular.ttf");
    private static final Font COMFORTAA_BOLD = loadFont("Comfortaa-Bold.ttf");
    private static final Font UNBOUNDED = loadFont("Unbounded-SemiBold.ttf");
    private static final Font UNBOUNDED_BOLD = loadFont("Unbounded-Bold.ttf");

    private final Library library;
    private Playlist chosenPlaylist;
    private final CardLayout cards = new CardLayout();
    private final JPanel deck = new JPanel(cards);
    private final MainPanel mainPanel;
    private final PlaylistsPanel playlistsPanel;
    private final SongsPanel songsPanel;
    private final DeletedSongsPanel deletedSongsPanel;
    private final DeletedPlaylistsPanel deletedPlaylistsPanel;

    /**
     * Builds the window and its five screens.
     *
     * @param library the Library every screen reads and changes
     */
    public HummWindow(Library library)
    {
        super("Humm");
        this.library = library;
        mainPanel = new MainPanel(this);
        playlistsPanel = new PlaylistsPanel(this);
        songsPanel = new SongsPanel(this);
        deletedSongsPanel = new DeletedSongsPanel(this);
        deletedPlaylistsPanel = new DeletedPlaylistsPanel(this);
        deck.add(mainPanel, MAIN);
        deck.add(playlistsPanel, PLAYLISTS);
        deck.add(songsPanel, SONGS);
        deck.add(deletedSongsPanel, DELETED_SONGS);
        deck.add(deletedPlaylistsPanel, DELETED_PLAYLISTS);
        add(deck);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        deck.setPreferredSize(new Dimension(1280, 720));
        pack();
        setMinimumSize(new Dimension(1000, 640));
        setLocationRelativeTo(null);
        showScreen(MAIN);
    }

    /**
     * Starts Humm. FlatLaf is set up before any Swing component exists.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            HummWindow window = new HummWindow(new Library());
            window.setVisible(true);
        });
    }

    /**
     * Gets the one shared Library.
     *
     * @return the Library
     */
    public Library getLibrary()
    {
        return library;
    }

    /**
     * Gets the playlist whose songs are open.
     *
     * @return the open playlist, or null when none is open
     */
    public Playlist getChosenPlaylist()
    {
        return chosenPlaylist;
    }

    /**
     * Remembers which playlist is open.
     *
     * @param playlist the playlist to open, or null for none
     */
    public void setChosenPlaylist(Playlist playlist)
    {
        chosenPlaylist = playlist;
    }

    /**
     * Shows one screen, rebuilding its rows first so it is never stale.
     * Songs screens fall back to the playlists screen when no playlist is
     * open.
     *
     * @param name one of the five screen names
     */
    public void showScreen(String name)
    {
        String target = name;
        if ((SONGS.equals(target) || DELETED_SONGS.equals(target))
            && chosenPlaylist == null)
        {
            target = PLAYLISTS;
        }
        switch (target)
        {
            case PLAYLISTS:
                playlistsPanel.refresh();
                break;
            case SONGS:
                songsPanel.refresh();
                break;
            case DELETED_SONGS:
                deletedSongsPanel.refresh();
                break;
            case DELETED_PLAYLISTS:
                deletedPlaylistsPanel.refresh();
                break;
            default:
                target = MAIN;
                break;
        }
        cards.show(deck, target);
    }

    // ---- small builders shared by every screen ----

    private static Font loadFont(String file)
    {
        try (InputStream in = HummWindow.class.getResourceAsStream(
            "/fonts/" + file))
        {
            if (in != null)
            {
                return Font.createFont(Font.TRUETYPE_FONT, in);
            }
        }
        catch (Exception e)
        {
            // a missing or broken font just means the plain default is used
        }
        return null;
    }


    private static Font pick(Font loaded, float size, int fallbackStyle)
    {
        Font base = loaded;
        if (base == null)
        {
            base = new Font(Font.SANS_SERIF, fallbackStyle, 12);
        }
        return base.deriveFont(size);
    }


    /**
     * Comfortaa, for body text, labels and buttons.
     *
     * @param size the size in points
     * @param bold true for the bold weight
     * @return the font
     */
    static Font body(float size, boolean bold)
    {
        return bold ? pick(COMFORTAA_BOLD, size, Font.BOLD)
            : pick(COMFORTAA, size, Font.PLAIN);
    }


    /**
     * Unbounded, for headings and the big title.
     *
     * @param size the size in points
     * @param bold true for the bold weight, false for semi-bold
     * @return the font
     */
    static Font heading(float size, boolean bold)
    {
        return bold ? pick(UNBOUNDED_BOLD, size, Font.BOLD)
            : pick(UNBOUNDED, size, Font.BOLD);
    }


    /**
     * Makes a label.
     *
     * @param text what it says
     * @param font the font
     * @param color the text color
     * @return the label
     */
    static JLabel label(String text, Font font, Color color)
    {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }


    /**
     * Makes a pill-shaped button.
     *
     * @param text what it says
     * @param background the fill color
     * @param foreground the text color
     * @return the button
     */
    static JButton button(String text, Color background, Color foreground)
    {
        JButton button = new JButton(text);
        button.setFont(body(14f, true));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.putClientProperty("JComponent.minimumHeight", 44);
        return button;
    }


    /**
     * Makes a rounded text field with grey hint text.
     *
     * @param hint the hint shown while the field is empty
     * @return the field
     */
    static JTextField field(String hint)
    {
        JTextField field = new JTextField();
        field.setFont(body(14f, false));
        field.putClientProperty("JTextField.placeholderText", hint);
        field.putClientProperty("JComponent.roundRect", true);
        field.putClientProperty("JComponent.minimumHeight", 44);
        return field;
    }


    /**
     * Finds an animated picture in src/main/resources/images.
     *
     * @param fileName the file, like "bird-main.gif"
     * @return the picture, or null if the file is not there yet
     */
    static Icon picture(String fileName)
    {
        URL url = HummWindow.class.getResource("/images/" + fileName);
        return url == null ? null : new ImageIcon(url);
    }


    /**
     * Makes a button that shows only a picture, like the bird. If the file
     * is not there yet, a plain placeholder button stands in.
     *
     * @param fileName the picture file
     * @param tip what the button does, for the hover tip
     * @return the button
     */
    static JButton pictureButton(String fileName, String tip)
    {
        Icon icon = picture(fileName);
        JButton button;
        if (icon == null)
        {
            button = button("[ " + fileName + " ]", TURQUOISE, INK);
            button.setPreferredSize(new Dimension(190, 150));
        }
        else
        {
            button = new JButton(icon);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        button.setToolTipText(tip);
        return button;
    }


    /**
     * A small colored bar, like the turquoise underline.
     *
     * @param width the width in pixels
     * @param height the height in pixels
     * @return the bar
     */
    static JPanel bar(int width, int height)
    {
        JPanel bar = new JPanel();
        bar.setBackground(TURQUOISE);
        bar.setPreferredSize(new Dimension(width, height));
        bar.setMaximumSize(new Dimension(width, height));
        return bar;
    }


    /**
     * A page heading: a big title, an optional line under it, and the
     * turquoise underline.
     *
     * @param title the title label
     * @param subtitle the line under the title, or null for none
     * @param onRight true to line everything up on the right
     * @return the heading
     */
    static JPanel heading(JLabel title, JLabel subtitle, boolean onRight)
    {
        float side = onRight ? JComponent.RIGHT_ALIGNMENT
            : JComponent.LEFT_ALIGNMENT;
        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        title.setAlignmentX(side);
        heading.add(title);
        if (subtitle != null)
        {
            subtitle.setAlignmentX(side);
            heading.add(javax.swing.Box.createVerticalStrut(6));
            heading.add(subtitle);
        }
        JPanel underline = bar(64, 6);
        underline.setAlignmentX(side);
        heading.add(javax.swing.Box.createVerticalStrut(10));
        heading.add(underline);
        return heading;
    }


    /**
     * A row of buttons, centered.
     *
     * @param buttons the buttons, left to right
     * @return the row
     */
    static JPanel buttonRow(JButton... buttons)
    {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        row.setOpaque(false);
        for (JButton button : buttons)
        {
            row.add(button);
        }
        return row;
    }


    /**
     * The text shown when a list is empty.
     *
     * @param text what to say
     * @return the label
     */
    static JLabel emptyState(String text)
    {
        JLabel label = label(text, body(15f, false), MUTED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(48, 0, 0, 0));
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        return label;
    }


    /**
     * Keeps a row from stretching taller than it needs to.
     *
     * @param row the row
     */
    static void fixHeight(JComponent row)
    {
        row.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, row.getPreferredSize().height));
    }


    /**
     * One white rounded row: a bold title and a muted line on the left, and
     * whatever goes on the right (a count, buttons).
     *
     * @param title the bold line
     * @param detail the muted line under it
     * @param right what goes on the right, left to right
     * @return the row
     */
    static JPanel row(String title, String detail, JComponent... right)
    {
        Card row = new Card(WHITE, 16);
        row.setLayout(new BorderLayout(16, 0));
        row.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel top = label(title, body(17f, true), INK);
        top.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        top.setToolTipText(title);
        text.add(top);
        JLabel under = label(detail.isEmpty() ? " " : detail, body(13f, false),
            MUTED);
        under.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        text.add(under);
        row.add(text, BorderLayout.CENTER);
        JPanel side = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        side.setOpaque(false);
        for (JComponent part : right)
        {
            side.add(part);
        }
        row.add(side, BorderLayout.EAST);
        fixHeight(row);
        return row;
    }


    /**
     * Fills a list with rows, or with a message when there are none.
     *
     * @param list the list to fill
     * @param rows the rows
     * @param emptyText what to say when there are no rows
     */
    static void fill(JPanel list, List<JPanel> rows, String emptyText)
    {
        list.removeAll();
        if (rows.isEmpty())
        {
            list.add(emptyState(emptyText));
        }
        for (JPanel row : rows)
        {
            row.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            list.add(row);
            list.add(javax.swing.Box.createVerticalStrut(12));
        }
        list.revalidate();
        list.repaint();
    }


    /**
     * Wraps a list in a scroll frame that keeps the same size however many
     * rows there are.
     *
     * @param list the list of rows
     * @return the scroll frame
     */
    static JScrollPane scroller(JPanel list)
    {
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }


    /**
     * The pop-up form that opens from the bird: a heading, labeled fields,
     * an error line, and Cancel / OK buttons.
     *
     * @param title the card's heading
     * @param names the label over each field
     * @param fields the fields, in the same order
     * @param message the error label
     * @param cancel the Cancel button
     * @param ok the button that submits
     * @return the card
     */
    static JPanel formCard(String title, String[] names, JTextField[] fields,
        JLabel message, JButton cancel, JButton ok)
    {
        Card card = new Card(LAVENDER, 20);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel heading = label(title, heading(16f, false), PURPLE);
        heading.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        card.add(heading);
        for (int i = 0; i < fields.length; i++)
        {
            card.add(javax.swing.Box.createVerticalStrut(12));
            JLabel name = label(names[i], body(12f, false), MUTED);
            name.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            card.add(name);
            card.add(javax.swing.Box.createVerticalStrut(4));
            fields[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
            card.add(fields[i]);
        }
        card.add(javax.swing.Box.createVerticalStrut(8));
        message.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        card.add(message);
        card.add(javax.swing.Box.createVerticalStrut(8));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(cancel);
        buttons.add(ok);
        buttons.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        card.add(buttons);
        card.setPreferredSize(new Dimension(400, card.getPreferredSize().height));
        return card;
    }


    // ---- shared pieces ----

    /** A rounded panel with a thin border: the look of every row and pop-up. */
    static class Card
        extends JPanel
    {
        private final Color fill;
        private final int arc;

        /**
         * Makes a card.
         *
         * @param fill the fill color
         * @param arc how round the corners are
         */
        Card(Color fill, int arc)
        {
            this.fill = fill;
            this.arc = arc;
            setOpaque(false);
        }


        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.setColor(LINE);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
        }
    }


    /**
     * The page every screen is built on. It paints the background and the
     * big corner circles, and it floats an optional bird button and pop-up
     * card over the content.
     */
    static class Screen
        extends JPanel
    {
        private static final int MARGIN = 8;
        private static final int CIRCLE = 260;
        private static final int INNER = 150;
        private static final int PEEK = 80;

        private final List<int[]> corners = new ArrayList<>();
        private final List<Color[]> colors = new ArrayList<>();
        private JComponent content;
        private JButton bird;
        private JPanel card;
        private boolean birdOnLeft;

        /** Makes an empty page. */
        Screen()
        {
            setLayout(null);
            setBackground(BACKGROUND);
        }


        /**
         * Adds a big circle that peeks in from a corner.
         *
         * @param corner 0 top left, 1 top right, 2 bottom left, 3 bottom right
         * @param outer the big circle's color
         * @param inner the smaller circle's color
         */
        void addCircle(int corner, Color outer, Color inner)
        {
            corners.add(new int[] {corner});
            colors.add(new Color[] {outer, inner});
        }


        /**
         * Sets what is on the page, with the usual margins.
         *
         * @param content the page's content
         */
        void setContent(JComponent content)
        {
            this.content = content;
            content.setOpaque(false);
            content.setBorder(BorderFactory.createEmptyBorder(32, 56, 32, 56));
            assemble();
        }


        /**
         * Puts the bird button in a bottom corner, with a pop-up card above
         * it, and keeps the content clear of the bird.
         *
         * @param bird the bird button
         * @param onLeft true for the bottom left corner, false for the right
         * @param card the pop-up card, closed to start with
         */
        void setBird(JButton bird, boolean onLeft, JPanel card)
        {
            this.bird = bird;
            this.birdOnLeft = onLeft;
            this.card = card;
            card.setVisible(false);
            content.setBorder(BorderFactory.createEmptyBorder(32, 56,
                bird.getPreferredSize().height + 2 * MARGIN, 56));
            bird.addActionListener(e -> setCardOpen(!card.isVisible()));
            assemble();
        }


        /**
         * Opens or closes the pop-up card.
         *
         * @param open true to show it
         */
        void setCardOpen(boolean open)
        {
            if (card != null)
            {
                card.setVisible(open);
                revalidate();
                repaint();
            }
        }


        private void assemble()
        {
            removeAll();
            // the first child is painted on top
            if (card != null)
            {
                add(card);
            }
            if (bird != null)
            {
                add(bird);
            }
            if (content != null)
            {
                add(content);
            }
        }


        @Override
        public void doLayout()
        {
            int w = getWidth();
            int h = getHeight();
            if (content != null)
            {
                content.setBounds(0, 0, w, h);
            }
            if (bird != null)
            {
                Dimension b = bird.getPreferredSize();
                int x = birdOnLeft ? MARGIN : w - b.width - MARGIN;
                int y = h - b.height - MARGIN;
                bird.setBounds(x, y, b.width, b.height);
                Dimension c = card.getPreferredSize();
                int cx = birdOnLeft ? 56 : w - c.width - 56;
                card.setBounds(cx, Math.max(y - c.height - 12, 8), c.width,
                    c.height);
            }
        }


        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < corners.size(); i++)
            {
                int corner = corners.get(i)[0];
                int x = (corner % 2 == 0) ? -PEEK : getWidth() - CIRCLE + PEEK;
                int y = (corner < 2) ? -PEEK : getHeight() - CIRCLE + PEEK;
                g2.setColor(colors.get(i)[0]);
                g2.fillOval(x, y, CIRCLE, CIRCLE);
                g2.setColor(colors.get(i)[1]);
                g2.fillOval(x + (CIRCLE - INNER) / 2, y + (CIRCLE - INNER) / 2,
                    INNER, INNER);
            }
            g2.dispose();
        }
    }


    /** A column of rows that is always as wide as its scroll frame. */
    static final class ListPanel
        extends JPanel
        implements Scrollable
    {
        /** Makes an empty list. */
        ListPanel()
        {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
        }


        @Override
        public Dimension getPreferredScrollableViewportSize()
        {
            return getPreferredSize();
        }


        @Override
        public int getScrollableUnitIncrement(Rectangle r, int o, int d)
        {
            return 18;
        }


        @Override
        public int getScrollableBlockIncrement(Rectangle r, int o, int d)
        {
            return Math.max(r.height - 40, 18);
        }


        @Override
        public boolean getScrollableTracksViewportWidth()
        {
            return true;
        }


        @Override
        public boolean getScrollableTracksViewportHeight()
        {
            return false;
        }
    }
}