package Database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: Anh Nguyen
 * @version: 6/6/2018
 *
 * Description: Class to interact with database
 * This class is set up for Image Library story
 * It helps user to update image to the database
 */

public class ItemDB extends Database {

       private static final String UPDATE_ITEM = "UPDATE ITEM SET Img=? WHERE Name = ?";

    /**
     * Update image to the database
     *
     * @param itemName the name to search for in the database
     * @param image The image to add into the database
     * @return
     */
    public boolean updateItemByName(String itemName, BufferedImage image) {
        super.connect();
        try {
            PreparedStatement stmt = super.mConnection.prepareStatement(UPDATE_ITEM);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = null;
            int length = 0;

            try {
                if (image != null) {
                    ImageIO.write(image, "png", outputStream);
                    inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    length = inputStream.available();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            stmt.setBinaryStream(1, inputStream, length);
            stmt.setString(2, itemName);
            stmt.executeUpdate();
            super.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            super.close();
            return false;
        }
    }
}
