package UserTakingTest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
