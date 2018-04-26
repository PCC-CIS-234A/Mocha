package ResultReporting;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Bobby Puckett
 * @version 4/25/2018
 * Description: Provides a way to communicate with the database. Inherits from base database class
 */
public class DatabaseConnectionResults {
    private ArrayList<User> users;

    public DatabaseConnectionResults() {
        users = new ArrayList<>();

        users.add(new User(1, "Bob", "things", "other@gmail.com", "user"));
        users.add(new User(2, "Gerald", "other", "things@gmail.com", "user"));

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Green", 3,1,0));
        itemList.add(new Item("Red", 2,2,0));
        itemList.add(new Item("Blue", 1,3,0));
        itemList.add(new Item("Brown", 0,4,0));

        Test test = new Test(itemList, "1.a", new Date());

        users.get(0).getTests().add(test);

        itemList.clear();

        itemList.add(new Item("Brown", 4,0,0));
        itemList.add(new Item("Green", 2,2,0));
        itemList.add(new Item("Red", 1,3,0));
        itemList.add(new Item("Blue", 0,4,0));

        test = new Test(itemList, "2.b", new Date());

        users.get(1).getTests().add(test);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
