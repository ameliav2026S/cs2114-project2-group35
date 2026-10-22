package humm;

import com.formdev.flatlaf.FlatDarkLaf;
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

    static final Color PURPLE = new Color(0x8355E2);
    static final Color HEAD = new Color(0xB39BFF);
    static final Color INK = new Color(0x1E1033);
    static final Color TEXT = new Color(0xF3EEFF);
    static final Color MUTED = new Color(0xB6A8D6);
    static final Color LAVENDER = new Color(0x2A1B4D);
    static final Color ROW = new Color(0x231640);
    static final Color LINE = new Color(0x3A2A63);
    static final Color CARD_LINE = new Color(0x44327A);
    static final Color FIELD = new Color(0x1A0F30);
    static final Color FIELD_LINE = new Color(0x4A3880);
    static final Color TURQUOISE = new Color(0x3EE0D0);
    static final Color BACKGROUND = new Color(0x150B26);
    static final Color ALERT = new Color(0xD64545);
    static final Color ALERT_TEXT = new Color(0xFF8A8A);
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
        // never smaller than this, so every word and button always fits
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        showScreen(MAIN);
    }

    /**
     * Starts Humm. FlatLaf is set up before any Swing component exists.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        FlatDarkLaf.setup();
        javax.swing.UIManager.put("ScrollBar.thumb", new Color(0x5B4796));
        javax.swing.UIManager.put("ScrollBar.track", new Color(0x150B26));
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
        JLabel label = new Word(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }


    /**
     * Makes a label sit in the middle of whatever room it has.
     *
     * @param label the label
     * @return the same label
     */
    static JLabel centered(JLabel label)
    {
        label.setHorizontalAlignment(SwingConstants.CENTER);
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
        return new Pill(text, background, foreground);
    }


    /**
     * Shows a message in a label, wrapped so none of it is cut off.
     *
     * @param label the label
     * @param text the message
     */
    static void say(JLabel label, String text)
    {
        String safe = text.replace("&", "&amp;").replace("<", "&lt;")
            .replace(">", "&gt;");
        label.setText("<html><body style='width: 340px'>" + safe
            + "</body></html>");
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
        field.putClientProperty("JComponent.outline", FIELD_LINE);
        field.setBackground(FIELD);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
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
            // no padding at all, so the picture can sit right on the edge
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setMargin(new java.awt.Insets(0, 0, 0, 0));
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
        if (onRight)
        {
            // the spare room ends up on the left, so words still end at the bar
            title.setHorizontalAlignment(SwingConstants.RIGHT);
        }
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
     * A page heading with the title in the middle and the turquoise
     * underline centered under it.
     *
     * @param title the title label
     * @return the heading
     */
    static JPanel centeredHeading(JLabel title)
    {
        JPanel heading = new JPanel(new java.awt.GridBagLayout());
        heading.setOpaque(false);
        java.awt.GridBagConstraints at = new java.awt.GridBagConstraints();
        at.gridx = 0;
        at.gridy = java.awt.GridBagConstraints.RELATIVE;
        centered(title);
        heading.add(title, at);
        at.insets = new java.awt.Insets(10, 0, 0, 0);
        heading.add(bar(64, 6), at);
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
        Card row = new Card(ROW, 16);
        row.setLayout(new BorderLayout(16, 0));
        row.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel top = label(title, body(17f, true), TEXT);
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
        Card card = new Card(LAVENDER, 20)
        {
            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(400, super.getPreferredSize().height);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel heading = label(title, heading(16f, false), HEAD);
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
        return card;
    }


    /**
     * A label that always asks for a little more room than its words need.
     * Swing sometimes measures text a few pixels short, and then swaps the
     * last letter for "..."; the spare room keeps that from happening.
     */
    static class Word
        extends JLabel
    {
        /**
         * Makes a label.
         *
         * @param text what it says
         */
        Word(String text)
        {
            super(text);
        }


        @Override
        public Dimension getPreferredSize()
        {
            Dimension d = super.getPreferredSize();
            return new Dimension(d.width + Math.round(getFont().getSize2D()
                * 0.5f), d.height);
        }


        @Override
        public Dimension getMinimumSize()
        {
            return getPreferredSize();
        }


        @Override
        public Dimension getMaximumSize()
        {
            // without this, a box layout squeezes the label back down to the
            // bare measured width and the spare room is lost
            return getPreferredSize();
        }
    }


    /**
     * A pill-shaped button that paints itself, so it looks the same with or
     * without FlatLaf. A white button gets an outline in its text color; any
     * other color is a solid fill. It always sizes itself to fit its words.
     */
    static class Pill
        extends JButton
    {
        private static final int SIDE = 24;
        private final Color fill;
        private final Color ink;

        /**
         * Makes a pill.
         *
         * @param text what it says
         * @param fill the fill color (white means outline only)
         * @param ink the text color
         */
        Pill(String text, Color fill, Color ink)
        {
            super(text);
            this.fill = fill;
            this.ink = ink;
            setFont(body(14f, true));
            setForeground(ink);
            setBorder(BorderFactory.createEmptyBorder());
            setMargin(new java.awt.Insets(0, 0, 0, 0));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }


        @Override
        public Dimension getPreferredSize()
        {
            if (isPreferredSizeSet())
            {
                return super.getPreferredSize();
            }
            java.awt.FontMetrics fm = getFontMetrics(getFont());
            return new Dimension(fm.stringWidth(getText()) + 2 * SIDE
                + Math.round(getFont().getSize2D() * 0.5f),
                Math.max(44, fm.getHeight() + 20));
        }


        @Override
        public Dimension getMinimumSize()
        {
            return getPreferredSize();
        }


        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            if (!isEnabled())
            {
                g2.setComposite(java.awt.AlphaComposite.getInstance(
                    java.awt.AlphaComposite.SRC_OVER, 0.4f));
            }
            int w = getWidth();
            int h = getHeight();
            if (!WHITE.equals(fill))
            {
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, w, h, h, h);
            }
            else
            {
                g2.setColor(ink);
                g2.setStroke(new java.awt.BasicStroke(2f));
                g2.drawRoundRect(1, 1, w - 2, h - 2, h - 2, h - 2);
            }
            // the words, drawn by hand so they can never be cut to "..."
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2.setFont(getFont());
            g2.setColor(ink);
            java.awt.FontMetrics fm = g2.getFontMetrics();
            int x = (w - fm.stringWidth(getText())) / 2;
            int y = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
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
            g2.setColor(LAVENDER.equals(fill) ? CARD_LINE : LINE);
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
                // the bird sits right on the edge so its stem touches the bottom
                int x = birdOnLeft ? 0 : w - b.width;
                int y = h - b.height;
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