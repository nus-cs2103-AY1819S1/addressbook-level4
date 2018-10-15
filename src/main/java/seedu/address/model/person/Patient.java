package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final IcNumber icNumber;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Medical Record
    private MedicalRecord medicalRecord;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, IcNumber icNumber, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, icNumber, phone, email, address, tags);
        this.name = name;
        this.icNumber = icNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.medicalRecord = new MedicalRecord();
    }

    public Patient(Name name, IcNumber icNumber, Phone phone, Email email, Address address,
                   Set<Tag> tags, MedicalRecord medicalRecord) {
        requireAllNonNull(name, icNumber, phone, email, address, tags, medicalRecord);
        this.name = name;
        this.icNumber = icNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.medicalRecord = medicalRecord;
    }

    /**
     * Make new Patient with specified MedicalRecord. MedicalRecord will combine with current MedicalRecord, if any.
     */
    public Patient(Patient patient, MedicalRecord medicalRecord) {
        requireNonNull(patient);
        requireNonNull(medicalRecord);

        this.name = patient.getName();
        this.icNumber = patient.getIcNumber();
        this.phone = patient.getPhone();
        this.email = patient.getEmail();
        this.address = patient.getAddress();
        this.tags.addAll(patient.getTags()); // Dunno if this works? Cuz .getTags() is returning unmodifiable set,
        this.medicalRecord = patient.getMedicalRecord() == null
                ? medicalRecord
                : MedicalRecord.combineMedicalRecords(patient.getMedicalRecord(), medicalRecord);
    }

    public Name getName() {
        return name;
    }

    public IcNumber getIcNumber() {
        return icNumber;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Add a new medical record to the patient. Patient's current medical record must be null.
     * @param medicalRecord
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        assert(this.medicalRecord == null);
        this.medicalRecord = medicalRecord;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName()) && otherPatient.getIcNumber().equals(getIcNumber())
                && (otherPatient.getPhone().equals(getPhone()) || otherPatient.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getIcNumber().equals(getIcNumber())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getEmail().equals(getEmail())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, icNumber, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" IC Number: ")
                .append(getIcNumber())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Console view of patient.
     * @return String representation of patient with Name and IcNumber.
     */
    public String toNameAndIc() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + " [")
                .append(getIcNumber() + "]");
        return builder.toString();
    }

}
