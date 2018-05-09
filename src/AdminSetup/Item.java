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

    private TableAction act;

    public enum TableAction {KEEP, INS, DEL;}


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



    public TableAction getTableAction() {
        return act;
    }





    public void setTestID(int testID) {
        myTestID = testID;
    }

    public void setName(String name) {
        myName = name;
    }




    public void setTableAction(TableAction action) {
        act = action;
    }




    public static ArrayList<Item> getTestItems(int testID) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestItems(testID);
    }



}
