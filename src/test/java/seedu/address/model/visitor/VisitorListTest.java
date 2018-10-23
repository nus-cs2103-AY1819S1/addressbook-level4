package seedu.address.model.visitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void equals_emptyVisitorListAndNull_returnsFalse() {
        assertFalse(new VisitorList().equals(null));
    }

    @Test
    public void equals_objectEqualsItself_returnsTrue() {
        VisitorList visitorList = new VisitorList();
        assertTrue(visitorList.equals(visitorList));
    }
    //Todo: cant add further tests, needs to modify visitorList structure;
}
