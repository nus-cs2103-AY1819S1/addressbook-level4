package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author snajef
/**
 * Test driver for the XmlAdaptedDose class.
 * @author Darien Chong
 *
 */
public class XmlAdaptedDoseTest {
    private static final double VALID_DOSE = 2.0;
    private static final String VALID_DOSE_UNIT = "tablets";
    private static final int VALID_DOSES_PER_DAY = 4;

    private static final double INVALID_DOSE = -2.0;
    private static final int INVALID_DOSES_PER_DAY = -4;

    private XmlAdaptedDose xad;

    @Before
    public void setup() throws IllegalValueException {
        xad = new XmlAdaptedDose(VALID_DOSE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY);
    }

    @Test(expected = IllegalValueException.class)
    public void constructor_negativeDose_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedDose(INVALID_DOSE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY);
    }

    @Test(expected = IllegalValueException.class)
    public void constructor_negativeDosesPerDay_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedDose(VALID_DOSE, VALID_DOSE_UNIT, INVALID_DOSES_PER_DAY);
    }

    @Test
    public void equals_itselfAndItself_returnsTrue() throws IllegalValueException {
        assertTrue(xad.equals(xad));
    }

    @Test
    public void equals_differentType_returnsFalse() throws IllegalValueException {
        assertFalse(xad.equals(1));
    }

    @Test
    public void equals_originalAndCopyOfModelType_returnsTrue() throws IllegalValueException {
        XmlAdaptedDose anotherXmlAdaptedDose = new XmlAdaptedDose(
            xad.toModelType());
        assertTrue(xad.equals(anotherXmlAdaptedDose));
    }

    @Test
    public void equals_constructedUsingDifferentValues_returnsFalse() throws IllegalValueException {
        XmlAdaptedDose anotherXmlAdaptedDose = new XmlAdaptedDose(VALID_DOSE, "not tablets", VALID_DOSES_PER_DAY);
        assertFalse(xad.equals(anotherXmlAdaptedDose));
    }
}
