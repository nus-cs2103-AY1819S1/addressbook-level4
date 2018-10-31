package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.appointment.Status;
import seedu.address.model.doctor.Doctor;

/**
 * JAXB-friendly adapted version of the Appointment.
 */
public class XmlAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private int appointmentId;
    @XmlElement(required = true)
    private String doctor;
    @XmlElement(required = true)
    private String patient;
    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String status;
    @XmlElement
    private String comments;
    @XmlElement
    private List<XmlAdaptedPrescription> prescriptions;

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs a {@code XmlAdaptedAppointment} with the given {@code prescriptionName}.
     */
    public XmlAdaptedAppointment(int appointmentId, String doctor, String patient, String dateTime, String status,
                                 String comments, List<XmlAdaptedPrescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = status;
        this.comments = comments;
        this.prescriptions = prescriptions;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment source) {
        appointmentId = source.getAppointmentId();
        doctor = source.getDoctor();
        patient = source.getPatient();
        dateTime = source.getDateTime().toString();
        status = source.getStatus().name();
        comments = source.getComments();
        prescriptions = source.getPrescriptions().stream()
                .map(XmlAdaptedPrescription::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted Appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Appointment toModelType() throws IllegalValueException {
        final List<Prescription> appointmentPrescriptions = new ArrayList<>();
        if (prescriptions != null && prescriptions.size() > 0) {
            for (XmlAdaptedPrescription prescription : prescriptions) {
                appointmentPrescriptions.add(prescription.toModelType());
            }
        }

        if (appointmentId == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentId.class.getSimpleName()));
        }
        final AppointmentId currentAppointmentId = new AppointmentId(appointmentId);

        if (doctor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Doctor.class.getSimpleName()));
        }

        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Doctor.class.getSimpleName()));
        }

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        final LocalDateTime appointmentDateTime = LocalDateTime.parse(dateTime);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        Status appointmentStatus = Status.UPCOMING;
        if (status == "COMPLETED") {
            appointmentStatus = Status.COMPLETED;
        }

        return new Appointment(currentAppointmentId, doctor, patient, appointmentDateTime,
                appointmentStatus, comments, appointmentPrescriptions);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        return appointmentId == ((XmlAdaptedAppointment) other).getAppointmentId();
    }
}
