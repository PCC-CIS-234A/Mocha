package AdminSetup.ImageLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by SYTC307u8365 on 10/5/2017.
 */

public class PicturePanel extends JPanel {
    private BufferedImage image = null;

    public PicturePanel() {
        super();
    }

    public void setImage(BufferedImage value) {
        image = value;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();

        g.clearRect(0, 0, panelWidth - 1, panelHeight - 1);

        if (image != null) {
            // Draw image
            g.drawImage(image, 0, 0, panelWidth, panelHeight, null);
        }

        // Draw frame around panel
        g.drawRect(0, 0, panelWidth - 1, panelHeight - 1);
    }
}
