package seedu.clinicio.storage;

import static org.junit.Assert.assertEquals;
import static seedu.clinicio.storage.XmlAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.ALEX_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CAT;

import org.junit.Test;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.testutil.Assert;


public class XmlAdaptedAppointmentTest {
    private static final String INVALID_DATE = "122 24 2001";
    private static final String INVALID_TIME = "652 00";

    private static final String VALID_DATE = "12 12 2001";
    private static final String VALID_TIME = "12 00";

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(ALEX_APPT);
        assertEquals(ALEX_APPT, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(INVALID_DATE, VALID_TIME,
                new XmlAdaptedPatient(ALEX), 1, 1, new XmlAdaptedStaff(CAT));
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(null, VALID_TIME,
                new XmlAdaptedPatient(ALEX), 1, 1, new XmlAdaptedStaff(CAT));
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(VALID_DATE, INVALID_TIME,
                new XmlAdaptedPatient(ALEX), 1, 1, new XmlAdaptedStaff(CAT));
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(VALID_DATE, null, new XmlAdaptedPatient(ALEX),
                1, 1, new XmlAdaptedStaff(CAT));
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
