package seedu.address.model.visitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;

public class VisitorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String visitorname1;
    private String visitorname2;

    @Before
    public void setUp() {
        visitorname1 = "GAO";
        visitorname2 = "XXX";
    }

    @Test
    public void constructor_nullType_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Visitor((Visitor) null));
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        Visitor visitor1 = new Visitor(visitorname1);
        assertTrue(visitor1.equals(visitor1));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Visitor((String) null));
    }

    @Test
    public void equals() {
        Visitor visitor1 = new Visitor(visitorname1);
        Visitor visitor2 = new Visitor(visitorname2);

        // equal to itself
        assertTrue(visitor1.equals(visitor1));

        // case for two different visitor name
        assertFalse(visitor1.equals(visitor2));
    }
}
