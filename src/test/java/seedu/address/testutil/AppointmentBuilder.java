package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.person.Person;

//@@author gingivitiss
/**
 * A utility class to help build Appointment objects.
 */
public class AppointmentBuilder {

    public static final Date DEFAULT_DATE = new Date(1, 1, 2018);
    public static final Time DEFAULT_TIME = new Time(16, 30);
    public static final Person DEFAULT_PATIENT = ALICE;

    private Date date;
    private Time time;
    private Person patient;

    public AppointmentBuilder() {
        date = DEFAULT_DATE;
        time = DEFAULT_TIME;
        patient = DEFAULT_PATIENT;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        date = appointmentToCopy.getAppointmentDate();
        time = appointmentToCopy.getAppointmentTime();
        patient = appointmentToCopy.getPatient();
    }

    /**
     * Sets the {@code Date} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(int day, int month, int year) {
        date = new Date(day, month, year);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTime(int hours, int minutes) {
        time = new Time(hours, minutes);
        return this;
    }

    /**
     * Sets the {@code Patient} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPerson(Person patient) {
        this.patient = patient;
        return this;
    }

    public Appointment build() {
        return new Appointment(date, time, patient);
    }
}
