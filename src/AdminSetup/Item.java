package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/2/2018.
 */
public class Item {
    private int myItemID;
    private int myTestID;
    private String myName;

  //  Item (int itemID, int testID, String name) {
  Item (int testID, String name) {
 //       myItemID = itemID;
        myTestID = testID;
        myName = name;
    }

 //   public int getItemID() {
//        return myItemID;
 //   }

    public int getTestID() {
        return myTestID;
    }

    public String getName() {
        return myName;
    }

//    public void setItemID(int itemID) {
//        myItemID = itemID;
//    }

    public void setTestID(int testID) {
        myTestID = testID;
    }

    public void setName(String name) {
        myName = name;
    }

}
