package humm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The main screen: the bird, the name, and a way in.
 *
 * @author Amelia Vasquez Rosario
 * @version October 2026
 */
public class MainPanel
    extends HummWindow.Screen
{
    /**
     * Builds the screen.
     *
     * @param window the window this screen lives in
     */
    public MainPanel(HummWindow window)
    {
        JPanel column = new JPanel(new GridBagLayout());
        GridBagConstraints at = new GridBagConstraints();
        at.gridx = 0;
        at.gridy = GridBagConstraints.RELATIVE;
        at.insets = new Insets(10, 0, 10, 0);

        Icon bird = HummWindow.picture("humm2.GIF");
        if (bird != null)
        {
            column.add(new JLabel(bird), at);
        }

        column.add(HummWindow.label("Humm", HummWindow.heading(88f, true),
            HummWindow.PURPLE), at);
        column.add(HummWindow.bar(120, 8), at);
        column.add(HummWindow.label("Playlists, kept close.",
            HummWindow.body(20f, false), HummWindow.MUTED), at);

        JButton open = HummWindow.button("Open Playlists", HummWindow.PURPLE,
            HummWindow.WHITE);
        open.setFont(HummWindow.body(18f, true));
        open.addActionListener(e -> window.showScreen(HummWindow.PLAYLISTS));
        at.insets = new Insets(26, 0, 10, 0);
        at.ipadx = 24;
        column.add(open, at);

        column.setOpaque(false);
        setContent(wrap(column));
    }


    private static JComponent wrap(JPanel column)
    {
        JPanel middle = new JPanel(new GridBagLayout());
        middle.add(column);
        return middle;
    }
}