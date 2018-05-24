package AdminSetup;

import SharedLogic.Test;

/**
 * @author Rebecca Kennedy
 * @version 5/23/2018.
 * Description: This class represents a test from the database table TEST.
 */
public class AdminSetupTest extends Test {
    private boolean editable = true;

    public AdminSetupTest(String name) {
        super(name);
    }

    /**
     * Gets the editable variable
     */
    public boolean getEditable() {
        return editable;
    }

    /**
     * Sets the editable variable
     */
    public void setEditable(boolean isEditable) {
        editable = isEditable;
    }
}
