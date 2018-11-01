package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Status;
import seedu.address.model.doctor.Doctor;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;

public class DoctorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addUpcomingAppointment() {
        Doctor doctor = new DoctorBuilder().build();

        // No appointment when first created
        assertTrue(doctor.getUpcomingAppointments().isEmpty());

        // Add an appointment
        Appointment appointmentToAdd = new AppointmentBuilder().build();
        Appointment randomAppointmentNotAdded = new AppointmentBuilder().withAppointmentId(123).build();
        doctor.addUpcomingAppointment(appointmentToAdd);

        // Upcoming Appointment no longer empty
        assertFalse(doctor.getUpcomingAppointments().isEmpty());

        // Appointment list contains the appointment that was added
        assertTrue(doctor.getUpcomingAppointments().get(0) == appointmentToAdd);
        assertNotSame(doctor.getUpcomingAppointments().get(0), randomAppointmentNotAdded);
    }

    @Test
    public void deleteUpcomingAppointment() {
        Doctor doctor = new DoctorBuilder().build();
        Appointment appointmentToAdd = new AppointmentBuilder().build();
        Appointment anotherAppointmentToAdd = new AppointmentBuilder().withComments("Hello").build();
        Appointment randomAppointmentNotAdded = new AppointmentBuilder().withAppointmentId(123).build();

        doctor.addUpcomingAppointment(appointmentToAdd);
        doctor.deleteAppointment(appointmentToAdd);

        // Check that appointment has been removed and ArrayList is empty
        assertTrue(doctor.getUpcomingAppointments().isEmpty());

        doctor.addUpcomingAppointment(appointmentToAdd);
        doctor.addUpcomingAppointment(anotherAppointmentToAdd);
        doctor.deleteAppointment(appointmentToAdd);

        // Check that appointment has been removed but ArrayList not empty
        assertFalse(doctor.getUpcomingAppointments().isEmpty());
        assertSame(doctor.getUpcomingAppointments().get(0), anotherAppointmentToAdd);

        // Delete random appointment
        doctor.deleteAppointment(randomAppointmentNotAdded);

        // Check that appointment is not removed
        assertFalse(doctor.getUpcomingAppointments().isEmpty());
        assertSame(doctor.getUpcomingAppointments().get(0), anotherAppointmentToAdd);
    }

    @Test
    public void completeAppointment() {
        Doctor doctor = new DoctorBuilder().build();
        Appointment appointmentToAdd = new AppointmentBuilder().build();
        Appointment anotherAppointmentToAdd = new AppointmentBuilder().withComments("Hello").build();
        Appointment moreAppointmentNotAdded = new AppointmentBuilder().withAppointmentId(123).build();

        doctor.addUpcomingAppointment(appointmentToAdd);
        doctor.addUpcomingAppointment(anotherAppointmentToAdd);
        doctor.addUpcomingAppointment(moreAppointmentNotAdded);

        doctor.completeUpcomingAppointment(appointmentToAdd);
        for (Appointment app : doctor.getUpcomingAppointments()) {
            assertFalse(app.getStatus().equals(Status.COMPLETED));
        }
    }

    @Test
    public void hasAppointment() {
        Doctor doctor = new DoctorBuilder().build();
        Appointment appointmentToAdd = new AppointmentBuilder().withAppointmentId(12345).build();
        Appointment appointmentNotToAdd = new AppointmentBuilder().withAppointmentId(123).build();
        doctor.addUpcomingAppointment(appointmentToAdd);

        assertTrue(doctor.hasAppointment(appointmentToAdd.getAppointmentId()));
        assertFalse(doctor.hasAppointment(appointmentNotToAdd.getAppointmentId()));
    }

    @Test
    public void hasClashForAppointment() {
        Doctor doctor = new DoctorBuilder().build();
        Appointment appointmentAdded = new AppointmentBuilder().withDateTime("2018-10-30 12:00").build();
        Appointment appointmentSameTime = new AppointmentBuilder().withDateTime("2018-10-30 12:00").build();
        Appointment appointmentSameTimeDiffDate = new AppointmentBuilder().withDateTime("2018-10-31 12:00").build();
        Appointment appointmentStartTimeClash = new AppointmentBuilder().withDateTime("2018-10-30 12:01").build();
        Appointment appointmentEndTimeClash = new AppointmentBuilder().withDateTime("2018-10-30 11:59").build();
        Appointment appointmentRightAfter = new AppointmentBuilder().withDateTime("2018-10-30 12:30").build();
        doctor.addUpcomingAppointment(appointmentAdded);

        assertTrue(doctor.hasClashForAppointment(appointmentSameTime));
        assertFalse(doctor.hasClashForAppointment(appointmentSameTimeDiffDate));
        assertTrue(doctor.hasClashForAppointment(appointmentStartTimeClash));
        assertTrue(doctor.hasClashForAppointment(appointmentEndTimeClash));
        assertFalse(doctor.hasClashForAppointment(appointmentRightAfter));
    }
}
