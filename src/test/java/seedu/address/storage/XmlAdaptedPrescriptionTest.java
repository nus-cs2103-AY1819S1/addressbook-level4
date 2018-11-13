package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;

public class XmlAdaptedPrescriptionTest {
    public static final String VALID_NRIC = BENSON.getNric().toString();
    public static final String VALID_DRUGNAME = "Artificial Tears";
    public static final double VALID_DOSAGE = 0.2;
    public static final String VALID_DOSE_UNIT = "ml";
    public static final int VALID_DOSES_PER_DAY = 4;
    public static final String VALID_START_DATE = "01-01-1990";
    public static final String VALID_END_DATE = "02-01-1990";

    // Note that VALID_DURATION_IN_DAYS must match the days between the start and end date, inclusive of the end date.
    public static final int VALID_DURATION_IN_DAYS = 2;

    private Dose dose;
    private Duration duration;
    private Prescription prescription;

    @Before
    public void setUp() throws IllegalValueException {
        dose = new Dose(VALID_DOSAGE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY);
        duration = new Duration(VALID_DURATION_IN_DAYS);
        prescription = new Prescription(VALID_NRIC, dose, duration);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPrescription() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(prescription);
        assertEquals(prescription, xmlAdaptedPrescription.toModelType());
    }

    @Test
    public void equals_itselfAndItself_returnsTrue() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
            VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_DAYS);
        assertTrue(xmlAdaptedPrescription.equals(xmlAdaptedPrescription));
    }

    @Test
    public void equals_differentType_returnsFalse() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
            VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_DAYS);
        assertFalse(xmlAdaptedPrescription.equals(1));
    }

    @Test
    public void equals_originalAndCopyOfModelType_returnsTrue() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
            VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_DAYS);
        XmlAdaptedPrescription anotherXmlAdaptedPrescription = new XmlAdaptedPrescription(
            xmlAdaptedPrescription.toModelType());
        assertTrue(xmlAdaptedPrescription.equals(anotherXmlAdaptedPrescription));
    }

    @Test
    public void equals_constructedUsingDifferentValues_returnsFalse() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
            VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_DAYS);
        XmlAdaptedPrescription anotherXmlAdaptedPrescription = new XmlAdaptedPrescription("Paracetalol", VALID_DOSAGE,
            VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_DAYS);
        assertFalse(xmlAdaptedPrescription.equals(anotherXmlAdaptedPrescription));
    }
}
