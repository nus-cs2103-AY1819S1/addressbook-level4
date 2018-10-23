package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;
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
    public void cancelAppointment() {
        Date validDate = new Date(2, 2, 2002);
        Time validTime = new Time(12, 20);

        Appointment appt = new Appointment(validDate, validTime, ALICE);
        Appointment appt2 = new Appointment(validDate, validTime, ALICE);

        appt.cancelAppointment();

        //0 means cancelled
        assertTrue(appt.getAppointmentStatus() == 0);
        assertFalse(appt.getAppointmentStatus() == appt2.getAppointmentStatus());
    }

    @Test
    public void isSameSlot() {
        Date validDate = new Date(2, 2, 2002);
        Date otherDate = new Date(3, 12, 2002);

        Time validTime = new Time(12, 20);
        Time otherTime = new Time(23, 10);

        Appointment appt = new Appointment(validDate, validTime, ALICE);
        Appointment appt2 = new Appointment(validDate, validTime, BOB);
        Appointment appt3 = new Appointment(otherDate, validTime, ALICE);
        Appointment appt4 = new Appointment(otherDate, otherTime, ALICE);

        //same obj
        assertTrue(appt.isSameSlot(appt));

        //same date and time
        assertTrue(appt.isSameSlot(appt2));

        //different date
        assertFalse(appt.isSameSlot(appt3));

        //different time
        assertFalse(appt3.isSameSlot(appt4));
    }

    @Test
    public void isSamePatient() {
        Date validDate = new Date(2, 2, 2222);

        Time validTime = new Time(12, 0);

        Appointment validAppt = new Appointment(validDate, validTime, ALICE);
        Appointment validAppt2 = new Appointment(validDate, validTime, BOB);
        Appointment validAppt3 = new Appointment(validDate, validTime, BOB);

        //same obj
        assertTrue(validAppt.isSamePatient(validAppt));

        //same patient
        assertTrue(validAppt3.isSamePatient(validAppt2));

        //diff patient
        assertFalse(validAppt.isSamePatient(validAppt2));
    }

    @Test
    public void isSameAppointment() {
        Date validDate = new Date(2, 2, 2222);
        Date otherDate = new Date(2, 3, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);

        Appointment validAppt = new Appointment(validDate, validTime, ALICE);
        Appointment validAppt2 = new Appointment(validDate, validTime, BOB);
        Appointment validAppt3 = new Appointment(validDate, validTime, BOB);
        Appointment validAppt4 = new Appointment(validDate, otherTime, ALICE);
        Appointment validAppt5 = new Appointment(otherDate, otherTime, ALICE);

        //same obj
        assertTrue(validAppt.isSameAppointment(validAppt));

        //same fields
        assertTrue(validAppt2.equals(validAppt3));

        //diff patient
        assertFalse(validAppt.isSameAppointment(validAppt2));

        //diff time slot
        assertFalse(validAppt.isSameAppointment(validAppt4));

        //diff date
        assertFalse(validAppt5.isSameAppointment(validAppt4));

        //diff status
        //status change has no effect
        validAppt3.cancelAppointment();
        assertTrue(validAppt3.isSameAppointment(validAppt2));
    }

    @Test
    public void isOverlapAppointment() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);
        Time otherTime1 = new Time(12, 30);

        Appointment validAppt = new Appointment(validDate, validTime, ALICE);
        Appointment validAppt2 = new Appointment(validDate, validTime, BOB);
        Appointment validAppt3 = new Appointment(validDate, otherTime, ALICE);
        Appointment validAppt4 = new Appointment(validDate, otherTime1, ALICE);

        //same timing
        assertTrue(validAppt.isOverlapAppointment(validAppt2));

        //this is later than appt but within duration
        assertTrue(validAppt3.isOverlapAppointment(validAppt4));

        //this is earlier than appt but within duration
        assertTrue(validAppt4.isOverlapAppointment(validAppt3));

        //1 hour from prev appt
        assertFalse(validAppt.isOverlapAppointment(validAppt3));
        assertFalse(validAppt3.isOverlapAppointment(validAppt2));
    }

    @Test
    public void isCancelled() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);
        Appointment validAppt = new Appointment(validDate, validTime, ALICE);

        validAppt.cancelAppointment();
        assertTrue(validAppt.isCancelled());
    }

    @Test
    public void equals() {
        Date validDate = new Date(2, 2, 2222);
        Date otherDate = new Date(2, 3, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);

        Appointment validAppt = new Appointment(validDate, validTime, ALICE);
        Appointment validAppt2 = new Appointment(validDate, validTime, BOB);
        Appointment validAppt3 = new Appointment(validDate, validTime, BOB);
        Appointment validAppt4 = new Appointment(validDate, otherTime, ALICE);
        Appointment validAppt5 = new Appointment(otherDate, otherTime, ALICE);

        //same obj
        assertTrue(validAppt.equals(validAppt));

        //same fields
        assertTrue(validAppt3.equals(validAppt2));

        //diff patient
        assertFalse(validAppt.equals(validAppt2));

        //diff date
        assertFalse(validAppt4.equals(validAppt5));

        //diff time
        assertFalse(validAppt.equals(validAppt4));

        //diff status
        validAppt2.cancelAppointment();
        assertFalse(validAppt2.equals(validAppt3));
    }
}
