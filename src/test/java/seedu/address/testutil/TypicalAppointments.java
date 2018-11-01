package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Prescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests
 */

public class TypicalAppointments {

    public static final Appointment FIRST = new AppointmentBuilder().withAppointmentId(10000)
            .withDoctor("John").withPatient("Amanda").withDateTime("2018-12-11 12:00")
            .withComments("Heart check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10000).withMedicineName("Aspirin").build()))).build();


    public static final Appointment SECOND = new AppointmentBuilder().withAppointmentId(10001)
            .withDoctor("John").withPatient("George").withDateTime("2018-12-12 12:00")
            .withComments("Flu check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10001).withMedicineName("Tamiflu").build()))).build();


    public static final Appointment THIRD = new AppointmentBuilder().withAppointmentId(10002)
            .withDoctor("Jane").withPatient("Alice").withDateTime("2018-12-13 12:00")
            .withComments("Flu check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10002).withMedicineName("Tamiflu").build()))).build();


    public static final Appointment FOURTH = new AppointmentBuilder().withAppointmentId(10003)
            .withDoctor("Jane").withPatient("Bob").withDateTime("2018-12-14 12:00")
            .withComments("Cough check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10003).withMedicineName("Dextromethorphan").build()))).build();


    public static final Appointment FIFTH = new AppointmentBuilder().withAppointmentId(10004)
            .withDoctor("John").withPatient("Patricia").withDateTime("2018-12-15 12:00")
            .withComments("Heart check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10004).withMedicineName("Aspirin").build()))).build();

    private TypicalAppointments() {} //prevents instantiation

    /**
     * Returns an {@Code AddressBook} with al the typical appointments
     */
    public static AddressBook getTypicalAddressBookWithAppointments() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(FIRST, SECOND, THIRD, FOURTH, FIFTH));
    }
}
