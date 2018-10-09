package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.person.medicalrecord.Note;

/**
 * Represents a patient that has already consulted the doctor in the address book.
 * It has all the necessary data needed to generate any object that inherits from Document.
 */
public class ServedPatient {

    private final Name name;
    private final IcNumber icNumber;
    private final List<Note> note;
    // add more fields as required

    /**
     * Constructs a servedPatient object from a patient object to extract all the information necessary for printing
     * a document.
     *
     * @param patient A valid patient.
     */
    public ServedPatient(Patient patient) {
        requireNonNull(patient);
        this.name = patient.getName();
        this.icNumber = patient.getIcNumber();
        this.note = patient.getMedicalRecord().getNotes();
    }

    public Name getName() {
        return name;
    }

    public IcNumber getIcNumber() {
        return icNumber;
    }

    public List<Note> getNote() {
        return note;
    }

}
