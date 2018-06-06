package ResultReporting;

import javax.swing.*;
import java.awt.*;


/**
 * @author Becky
 * @version 5/30/2018.
 * Displays cumulative statistics.
 */
public class CumulativeStatistics {
    private JPanel cumStatPanel;
    private JFrame frame;

    public CumulativeStatistics(JFrame frame) {
        cumStatPanel.setPreferredSize(new Dimension(500, 400));
        this.frame = frame;
    } //end constructor

    /**
     * Getter for cumStatPanel
     *
     * @return the cumStatPanel
     */
    public JPanel getcumStatPanel() {
        return cumStatPanel;
    }
}
