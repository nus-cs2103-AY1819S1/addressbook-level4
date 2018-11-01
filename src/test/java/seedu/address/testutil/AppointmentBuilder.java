package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.appointment.Status;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {
    public static final int DEFAULT_APPOINTMENT_ID = 12345;
    public static final String DEFAULT_DOCTOR = "JACKY";
    public static final String DEFAULT_PATIENT = "MARY";
    public static final String DEFAULT_DATE = "2018-10-30 12:00";
    public static final String DEFAULT_COMMENTS = "Heart appointment";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm";

    private AppointmentId appointmentId;
    private String doctor;
    private String patient;
    private LocalDateTime dateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    /**
     * Default constructor for the AppointmentBuilder.
     */
    public AppointmentBuilder() {
        appointmentId = new AppointmentId(DEFAULT_APPOINTMENT_ID);
        doctor = DEFAULT_DOCTOR;
        patient = DEFAULT_PATIENT;
        dateTime = LocalDateTime.parse(DEFAULT_DATE, DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
        status = status.UPCOMING;
        comments = DEFAULT_COMMENTS;
        prescriptions = new ArrayList<>();
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        appointmentId = new AppointmentId(appointmentToCopy.getAppointmentId());
        doctor = appointmentToCopy.getDoctor();
        patient = appointmentToCopy.getPatient();
        dateTime = appointmentToCopy.getDateTime();
        status = appointmentToCopy.getStatus();
        comments = appointmentToCopy.getComments();
        prescriptions = appointmentToCopy.getPrescriptions();
    }

    /**
     * Sets the {@code appointmentId} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withAppointmentId(int id) {
        this.appointmentId = new AppointmentId(id);
        return this;
    }

    /**
     * Parses the {@code prescription} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public AppointmentBuilder withPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    /**
     * Sets the {@code doctor} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDoctor(String doctor) {
        this.doctor = doctor;
        return this;
    }

    /**
     * Sets the {@code patient} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatient(String patient) {
        this.patient = patient;
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStatus(String status) {
        if (status.equals("COMPLETED")) {
            this.status = Status.COMPLETED;
        } else if (status.equals("UPCOMING")) {
            this.status = Status.UPCOMING;
        }
        return this;
    }

    /**
     * Sets the {@code comments} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withComments(String comments) {
        this.comments = comments;
        return this;
    }

    public Appointment build() {
        return new Appointment(appointmentId, doctor, patient, dateTime, status, comments, prescriptions);
    }
}
