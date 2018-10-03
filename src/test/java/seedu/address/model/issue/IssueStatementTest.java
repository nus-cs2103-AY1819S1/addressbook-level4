package seedu.address.model.issue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IssueStatementTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IssueStatement(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IssueStatement(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> IssueStatement.isValidIssueStatement(null));

        // invalid name
        assertFalse(IssueStatement.isValidIssueStatement("")); // empty string
        assertFalse(IssueStatement.isValidIssueStatement(" ")); // spaces only
        assertFalse(IssueStatement.isValidIssueStatement("^")); // only non-alphanumeric characters
        assertFalse(IssueStatement.isValidIssueStatement("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(IssueStatement.isValidIssueStatement("peter jack")); // alphabets only
        assertTrue(IssueStatement.isValidIssueStatement("12345")); // numbers only
        assertTrue(IssueStatement.isValidIssueStatement("peter the 2nd")); // alphanumeric characters
        assertTrue(IssueStatement.isValidIssueStatement("Capital Tan")); // with capital letters
        assertTrue(IssueStatement.isValidIssueStatement("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
