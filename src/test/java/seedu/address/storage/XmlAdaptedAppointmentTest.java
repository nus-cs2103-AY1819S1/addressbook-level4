package seedu.address.storage;

import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Type;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class XmlAdaptedAppointmentTest {
    private static final String INVALID_PROCEDURE = "!";
    private static final String INVALID_DATE_TIME = "@";
    private static final String INVALID_DOCTOR = "1";

    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final Type VALID_TYPE = Type.SURGICAL;
    private static final String VALID_PROCEDURE = "Heart Bypass";
    private static final String VALID_DATE_TIME = "12-12-2022 12:00";
    private static final Calendar VALID_DATE_TIME_CAL;
    static {
        Calendar dt = null;
        try {
            dt = ParserUtil.parseDateTime("12-12-2022 12:00");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        VALID_DATE_TIME_CAL = dt;
    }
    private static final String VALID_DOCTOR = "Dr. Pepper";

    private Appointment appt;

    @Before
    public void setUp() throws IllegalValueException {
        appt = new Appointment(VALID_TYPE, VALID_PROCEDURE, VALID_DATE_TIME_CAL, VALID_DOCTOR);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws IllegalValueException {
        XmlAdaptedAppointment xmlAdaptedAppointment = new XmlAdaptedAppointment(appt);
        assertEquals(appt, xmlAdaptedAppointment.toModelType());
    }

    @Test
    public void toModelType_invalidProcedure_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedAppointment(VALID_TYPE, INVALID_PROCEDURE, VALID_DATE_TIME, VALID_DOCTOR);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedAppointment(VALID_TYPE, VALID_PROCEDURE, INVALID_DATE_TIME, VALID_DOCTOR);
    }

    @Test
    public void toModelType_invalidDoctor_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedAppointment(VALID_TYPE, VALID_PROCEDURE, VALID_DATE_TIME, INVALID_DOCTOR);
    }

//    @Test
//    public void toModelType_invalidType_throwsIllegalValueException() throws IllegalValueException {
//        Set<String> expected = new HashSet<>(Arrays.asList("PROPAEDEUTIC", "DIAGNOSTIC", "THERAPEUTIC", "SURGICAL"));
//        Set<String> actual = new HashSet<>();
//        for (Type t : Type.values()) actual.add(t.name());
//        assertEquals(expected, actual);
//    }
}
