package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
    }

    @Test
    public void isSameSlot() {
        Date validDate = new Date(2, 2, 2002);
        Date otherDate = new Date(3, 12, 2002);
        Time validTime = new Time(12, 20);
        Time otherTime = new Time(23, 10);

        //date and time are the same
        Appointment appt = new Appointment(validDate, validTime, ALICE);
        Appointment appt2 = new Appointment(validDate, validTime, BOB);
        assertTrue(appt.isSameSlot(appt));
        assertTrue(appt.isSameSlot(appt2));

        //different
        Appointment appt3 = new Appointment(otherDate, otherTime, ALICE);
        assertFalse(appt.isSameSlot(appt3));

    }

    @Test
    public void isSamePatient() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);
        Appointment validAppt = new Appointment(validDate, validTime, ALICE);
        Appointment validAppt2 = new Appointment(validDate, validTime, BOB);
        Appointment valiAppt3 = new Appointment(validDate, validTime, BOB);

        //same obj
        assertTrue(validAppt.isSamePatient(validAppt));

        //same patient
        assertTrue(valiAppt3.isSamePatient(validAppt2));

        //diff patient
        assertFalse(validAppt.isSamePatient(validAppt2));
    }
}
