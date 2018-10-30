package seedu.clinicio.storage;

//@@author arsalanc-v2

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Staff;

/**
 * JAXB-friendly version of the Consultation.
 * WIP: Requires XMLAdaptedPatient
 */
public class XmlAdaptedConsultation {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Consultation's %s field is missing!";

    @XmlElement(required = true)
    private String patient;
    @XmlElement (required = true)
    private String date;
    @XmlElement (required = true)
    private String arrivalTime;

    @XmlElement
    private XmlAdaptedStaff doctor;
    @XmlElement
    private XmlAdaptedAppointment appointment;
    @XmlElement
    private String consultationTime;
    @XmlElement
    private String endTime;
    @XmlElement
    private String description;
    @XmlElement
    private String prescription;

    /**
     * Constructs an XmlAdaptedConsultation. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedConsultation() {
    }

    /**
     * Constructs an {@code XmlAdaptedConsultation} with the given consultation details.
     */
    public XmlAdaptedConsultation(Patient patient, Staff staff, Appointment appointment, String description,
        Date date, Time arrivalTime, Time consultationTime, Time endTime, String prescription) {
        this.patient = patient.toString();
        this.doctor = new XmlAdaptedStaff(staff);
        this.appointment = new XmlAdaptedAppointment(appointment);
        this.description = description;
        this.prescription = prescription;

        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        this.date = String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year);

        int arrivalHour = arrivalTime.getHour();
        int arrivalMin = arrivalTime.getMinute();
        this.arrivalTime = String.valueOf(arrivalHour) + " " + String.valueOf(arrivalMin);

        int consultationHour = consultationTime.getHour();
        int consultationMin = consultationTime.getMinute();
        this.consultationTime = String.valueOf(consultationHour) + " " + String.valueOf(consultationMin);

        int endHour = endTime.getHour();
        int endMin = endTime.getMinute();
        this.endTime = String.valueOf(endHour) + " " + String.valueOf(endMin);
    }

    /**
     * Converts a given Consultation into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedConsultation
     */
    public XmlAdaptedConsultation(Consultation source) {
        patient = source.getPatient().toString();
        doctor = new XmlAdaptedStaff(source.getDoctor().map(doc -> doc).orElse(null));
        appointment = new XmlAdaptedAppointment(source.getAppointment().get());
        description = source.getDescription().map(descript -> descript.toString()).orElse("");

        endTime = source.getEndTime().toString();
        prescription = source.getPrescription().toString();

        int day = source.getConsultationDate().getDay();
        int month = source.getConsultationDate().getMonth();
        int year = source.getConsultationDate().getYear();
        date = String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year);

        int arrivalHour = source.getArrivalTime().getHour();
        int arrivalMin = source.getArrivalTime().getMinute();
        arrivalTime = String.valueOf(arrivalHour) + " " + String.valueOf(arrivalMin);

        int consultationHour = source.getConsultationTime().map(time -> time.getHour()).orElse(null);
        int consultationMin = source.getConsultationTime().map(time -> time.getMinute()).orElse(null);
        consultationTime = String.valueOf(consultationHour) + " " + String.valueOf(consultationMin);

        int endHour = source.getEndTime().map(time -> time.getHour()).orElse(null);
        int endMin = source.getEndTime().map(time -> time.getMinute()).orElse(null);
        endTime = String.valueOf(endHour) + " " + String.valueOf(endMin);
    }

    /**
     * Converts this jaxb-friendly adapted consultation object into the model's consultation object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted consultation
     */
    public Consultation toModelType() throws IllegalValueException {
        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Patient.class.getSimpleName()));
        }
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(String.format(Date.MESSAGE_DATE_CONSTRAINTS));
        }
        if (arrivalTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "arrival " + Time.class
                .getSimpleName()));
        }
        if (!Time.isValidTime(arrivalTime)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "arrival " + Time.class
                .getSimpleName()));
        }

        /**return new Consultation(patient, staff, appointment, description, Date.newDate(date), Time.newTime
          (arrivalTime), Time.newTime(consultationTime), Time.newTime(endTime), prescription);
         */
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedConsultation)) {
            return false;
        }

        XmlAdaptedConsultation otherConsultation = (XmlAdaptedConsultation) other;
        return Objects.equals(patient, otherConsultation.patient)
            && Objects.equals(doctor, otherConsultation.doctor)
            && Objects.equals(appointment, otherConsultation.appointment)
            && Objects.equals(description, otherConsultation.description)
            && Objects.equals(doctor, otherConsultation.doctor)
            && Objects.equals(date, otherConsultation.date)
            && Objects.equals(arrivalTime, otherConsultation.arrivalTime)
            && Objects.equals(consultationTime, otherConsultation.consultationTime)
            && Objects.equals(endTime, otherConsultation.endTime)
            && Objects.equals(prescription, otherConsultation.prescription);
    }
}


