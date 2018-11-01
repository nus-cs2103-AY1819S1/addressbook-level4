package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.AppointmentBuilder;

public class AppointmentTest {

    @Test
    public void isLaterThan() {
        Appointment appointmentBase = new AppointmentBuilder().withDateTime("2018-10-30 15:00").build();
        Appointment appointmentSameTime = new AppointmentBuilder().withDateTime("2018-10-30 15:00").build();
        Appointment appointmentEarly = new AppointmentBuilder().withDateTime("2018-10-30 14:00").build();
        Appointment appointmentLater = new AppointmentBuilder().withDateTime("2018-10-30 16:00").build();

        assertTrue(appointmentBase.isLaterThan(appointmentEarly));

        assertFalse(appointmentBase.isLaterThan(appointmentSameTime));
        assertFalse(appointmentBase.isLaterThan(appointmentLater));
        assertFalse(appointmentBase.isLaterThan(appointmentBase));
    }

    @Test
    public void isSameAppointment() {
        Appointment appointmentBase = new AppointmentBuilder().withAppointmentId(10000).build();
        Appointment appointmentSameId = new AppointmentBuilder().withAppointmentId(10000).build();
        Appointment appointmentDifferentId = new AppointmentBuilder().withAppointmentId(10001).build();

        assertTrue(appointmentBase.isSameAppointment(appointmentBase));
        assertTrue(appointmentBase.isSameAppointment(appointmentSameId));

        assertFalse(appointmentBase.isSameAppointment(appointmentDifferentId));
    }

    @Test
    public void hasClassAppointment() {
        Appointment appointmentBase = new AppointmentBuilder().withDateTime("2018-10-30 12:00").build();
        Appointment appointmentSameTime = new AppointmentBuilder().withDateTime("2018-10-30 12:00").build();
        Appointment appointmentSameTimeDiffDate = new AppointmentBuilder().withDateTime("2018-10-31 12:00").build();
        Appointment appointmentStartTimeClash = new AppointmentBuilder().withDateTime("2018-10-30 12:01").build();
        Appointment appointmentEndTimeClash = new AppointmentBuilder().withDateTime("2018-10-30 11:59").build();
        Appointment appointmentRightAfter = new AppointmentBuilder().withDateTime("2018-10-30 12:30").build();

        assertTrue(appointmentBase.hasClashAppointment(appointmentSameTime));
        assertFalse(appointmentBase.hasClashAppointment(appointmentSameTimeDiffDate));
        assertTrue(appointmentBase.hasClashAppointment(appointmentStartTimeClash));
        assertTrue(appointmentBase.hasClashAppointment(appointmentEndTimeClash));
        assertFalse(appointmentBase.hasClashAppointment(appointmentRightAfter));
    }
}
