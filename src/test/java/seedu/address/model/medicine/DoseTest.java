package seedu.address.model.medicine;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author snajef
/**
 * Test driver class for Dose POJO class functionality.
 * @author Darien Chong
 *
 */
public class DoseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Dose(0, null, 0));
    }

    @Test
    public void isCorrectFuzzyStringRepresentation() {
        int singleDose = 1;
        int twoDoses = 2;
        int threeDoses = 3;
        int oneMillionDoses = 1_000_000;

        org.junit.Assert.assertEquals("once a day.", Dose.fuzzyStringRepresentation(singleDose));
        org.junit.Assert.assertEquals("twice a day.", Dose.fuzzyStringRepresentation(twoDoses));
        org.junit.Assert.assertEquals("thrice a day.", Dose.fuzzyStringRepresentation(threeDoses));
        org.junit.Assert.assertEquals("1000000 times a day.", Dose.fuzzyStringRepresentation(oneMillionDoses));
    }
}
