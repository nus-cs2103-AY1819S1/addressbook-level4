package seedu.address.model.patient;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    // Variables
    private String telegramId;
    private MedicalHistory medicalHistory;
    private Queue<Appointment> upcomingAppointments;
    private Stack<Appointment> pastAppointments;

    // Constructor
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, String telegramId) {
        super(name, phone, email, address, tags);
        setTelegramId(telegramId);
        upcomingAppointments = new LinkedList<>();
        pastAppointments = new Stack<>();
    }

    public String getTelegramId() {
        return telegramId;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * Adds allergy into the medical history of patient
     */
    public void addAllergy(String allergy) {
        medicalHistory.addAllergy(allergy);
    }

    /**
     * Adds condition into the medical history of patient
     */
    public void addCondition(String condition) {
        medicalHistory.addCondition(condition);
    }

    /**
     * Adds an upcoming appointment to the patient's queue of upcoming appointment.
     */
    public void addUpcomingAppointment(Appointment appointment) {
        upcomingAppointments.add(appointment);
    }

    /**
     * Completes the latest appointment of the patient, placing the records of the appointment in to the stack of
     * appointments
     */
    public void completeUpcomingAppointment() {
        Appointment completedAppointment = upcomingAppointments.remove();
        completedAppointment.completeAppointment();
        pastAppointments.push(completedAppointment);
    }
}
