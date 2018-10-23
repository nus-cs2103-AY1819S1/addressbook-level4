package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class XmlAdaptedPrescriptionListTest {
    private XmlAdaptedPrescriptionList xmlAdaptedPrescriptionList;
    private XmlAdaptedPrescription xmlAdaptedPrescription;

    @Before
    public void setUp() throws IllegalValueException {
        xmlAdaptedPrescriptionList = new XmlAdaptedPrescriptionList();
        xmlAdaptedPrescription = new XmlAdaptedPrescription(
            XmlAdaptedPrescriptionTest.VALID_DRUGNAME,
            XmlAdaptedPrescriptionTest.VALID_DOSAGE,
            XmlAdaptedPrescriptionTest.VALID_DOSE_UNIT,
            XmlAdaptedPrescriptionTest.VALID_DOSES_PER_DAY,
            XmlAdaptedPrescriptionTest.VALID_START_DATE,
            XmlAdaptedPrescriptionTest.VALID_END_DATE,
            XmlAdaptedPrescriptionTest.VALID_DURATION_IN_DAYS);
    }

    @Test
    public void setPrescription() {
        List<XmlAdaptedPrescription> newList = new ArrayList<>();
        newList.add(xmlAdaptedPrescription);

        xmlAdaptedPrescriptionList.setPrescription(newList);
        assertTrue(xmlAdaptedPrescriptionList.equals(new XmlAdaptedPrescriptionList(newList)));
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        assertTrue(xmlAdaptedPrescriptionList.equals(xmlAdaptedPrescriptionList));
    }

    @Test
    public void equals_objectAndDefensiveCopy_returnsTrue() {
        assertTrue(xmlAdaptedPrescriptionList.equals(new XmlAdaptedPrescriptionList(xmlAdaptedPrescriptionList)));
    }

    @Test
    public void equals_objectAndDifferentType_returnsFalse() {
        assertFalse(xmlAdaptedPrescriptionList.equals(1));
    }
}
