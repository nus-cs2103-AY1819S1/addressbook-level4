package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Status;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PatientBuilder;

public class PatientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addAllergy() {
        Patient patient = new PatientBuilder().build();
        patient.addAllergy("Nuts");

        // Check if added correctly
        assertTrue(patient.getMedicalHistory().getAllergies().contains("Nuts"));
        assertFalse(patient.getMedicalHistory().getAllergies().contains("Chicken"));

        patient.addAllergy("Chicken");
        assertTrue(patient.getMedicalHistory().getAllergies().contains("Chicken"));
    }

    @Test
    public void addCondition() {
        Patient patient = new PatientBuilder().build();
        patient.addCondition("Asthma");

        // Check if added correctly
        assertTrue(patient.getMedicalHistory().getConditions().contains("Asthma"));
        assertFalse(patient.getMedicalHistory().getConditions().contains("Heart Pain"));

        patient.addCondition("Heart Pain");
        assertTrue(patient.getMedicalHistory().getConditions().contains("Heart Pain"));
    }

    @Test
    public void addUpcomingAppointment() {
        Patient patient = new PatientBuilder().build();

        // No appointment when first created
        assertTrue(patient.getUpcomingAppointments().isEmpty());

        // Add an appointment
        Appointment appointmentToAdd = new AppointmentBuilder().build();
        Appointment randomAppointmentNotAdded = new AppointmentBuilder().withAppointmentId(123).build();
        patient.addUpcomingAppointment(appointmentToAdd);

        // Upcoming Appointment no longer empty
        assertFalse(patient.getUpcomingAppointments().isEmpty());

        // Appointment list contains the appointment that was added
        assertTrue(patient.getUpcomingAppointments().get(0) == appointmentToAdd);
        assertNotSame(patient.getUpcomingAppointments().get(0), randomAppointmentNotAdded);
    }

    @Test
    public void deleteUpcomingAppointment() {
        Patient patient = new PatientBuilder().build();
        Appointment appointmentToAdd = new AppointmentBuilder().build();
        Appointment anotherAppointmentToAdd = new AppointmentBuilder().withComments("Hello").build();
        Appointment randomAppointmentNotAdded = new AppointmentBuilder().withAppointmentId(123).build();

        patient.addUpcomingAppointment(appointmentToAdd);
        patient.deleteAppointment(appointmentToAdd);

        // Check that appointment has been removed and ArrayList is empty
        assertTrue(patient.getUpcomingAppointments().isEmpty());

        patient.addUpcomingAppointment(appointmentToAdd);
        patient.addUpcomingAppointment(anotherAppointmentToAdd);
        patient.deleteAppointment(appointmentToAdd);

        // Check that appointment has been removed but ArrayList not empty
        assertFalse(patient.getUpcomingAppointments().isEmpty());
        assertSame(patient.getUpcomingAppointments().get(0), anotherAppointmentToAdd);

        // Delete random appointment
        patient.deleteAppointment(randomAppointmentNotAdded);

        // Check that appointment is not removed
        assertFalse(patient.getUpcomingAppointments().isEmpty());
        assertSame(patient.getUpcomingAppointments().get(0), anotherAppointmentToAdd);
    }

    @Test
    public void completeAppointment() {
        Patient patient = new PatientBuilder().build();
        Appointment appointmentToComplete = new AppointmentBuilder().build();
        Appointment anotherAppointmentToComplete = new AppointmentBuilder().withAppointmentId(124).build();
        Appointment moreAppointmentNotComplete = new AppointmentBuilder().withAppointmentId(123).build();

        patient.addUpcomingAppointment(appointmentToComplete);
        patient.addUpcomingAppointment(anotherAppointmentToComplete);
        patient.addUpcomingAppointment(moreAppointmentNotComplete);

        patient.completeUpcomingAppointment(appointmentToComplete);
        for (Appointment app : patient.getUpcomingAppointments()) {
            assertFalse(app.getStatus().equals(Status.COMPLETED));
        }

        patient.completeUpcomingAppointment(anotherAppointmentToComplete);
        for (Appointment app : patient.getUpcomingAppointments()) {
            assertFalse(app.getStatus().equals(Status.COMPLETED));
        }
    }

    @Test
    public void hasAppointment() {
        Patient patient = new PatientBuilder().build();
        Appointment appointmentToAdd = new AppointmentBuilder().withAppointmentId(12345).build();
        Appointment appointmentNotToAdd = new AppointmentBuilder().withAppointmentId(123).build();
        patient.addUpcomingAppointment(appointmentToAdd);

        assertTrue(patient.hasAppointment(appointmentToAdd.getAppointmentId()));
        assertFalse(patient.hasAppointment(appointmentNotToAdd.getAppointmentId()));
    }

    @Test
    public void hasClashForAppointment() {
        Patient patient = new PatientBuilder().build();
        Appointment appointmentAdded = new AppointmentBuilder().withDateTime("2018-10-30 12:00").build();
        Appointment appointmentSameTime = new AppointmentBuilder().withDateTime("2018-10-30 12:00").build();
        Appointment appointmentSameTimeDiffDate = new AppointmentBuilder().withDateTime("2018-10-31 12:00").build();
        Appointment appointmentStartTimeClash = new AppointmentBuilder().withDateTime("2018-10-30 12:01").build();
        Appointment appointmentEndTimeClash = new AppointmentBuilder().withDateTime("2018-10-30 11:59").build();
        Appointment appointmentRightAfter = new AppointmentBuilder().withDateTime("2018-10-30 12:30").build();
        patient.addUpcomingAppointment(appointmentAdded);

        assertTrue(patient.hasClashForAppointment(appointmentSameTime));
        assertFalse(patient.hasClashForAppointment(appointmentSameTimeDiffDate));
        assertTrue(patient.hasClashForAppointment(appointmentStartTimeClash));
        assertTrue(patient.hasClashForAppointment(appointmentEndTimeClash));
        assertFalse(patient.hasClashForAppointment(appointmentRightAfter));
    }
}
