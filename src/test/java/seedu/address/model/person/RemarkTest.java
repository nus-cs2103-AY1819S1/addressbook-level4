package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void equals() {
        final Remark standardRemark = new Remark("Likes dogs");

        //same values -> returns true
        Remark remarkWithSameValue = new Remark("Likes dogs");
        assertTrue(standardRemark.equals(remarkWithSameValue));

        //same object -> returns true
        assertTrue(standardRemark.equals(standardRemark));

        //null -> returns false
        assertFalse(standardRemark.equals(null));

        //different types -> returns false
        assertFalse(standardRemark.equals(new Name("John")));

        //different descriptor -> returns false
        Remark remarkwithDiffValue = new Remark("Likes cats");
        assertFalse(standardRemark.equals(remarkwithDiffValue));
    }
}
