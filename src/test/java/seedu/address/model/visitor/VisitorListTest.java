package seedu.address.model.visitor;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Test class for VisitorList
 */
public class VisitorListTest {

    @Test(expected = NullPointerException.class)
    public void constructor_nullList_throwsNullPointerException() {
        new VisitorList((ArrayList<Visitor>) null);
    }
    //Todo: cant add further tests, needs to modify visitorList structure;
}
