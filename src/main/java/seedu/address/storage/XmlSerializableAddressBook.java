package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.Patient;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate patient(s).";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicines list contains duplicate medicine(s).";

    @XmlElement
    private List<XmlAdaptedPerson> patients;

    @XmlElement
    private List<XmlAdaptedMedicine> medicines;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        patients = new ArrayList<>();
        medicines = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        patients.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        medicines.addAll(src.getMedicineList().stream().map(XmlAdaptedMedicine::new).collect(Collectors.toList()));
    }

    public List<XmlAdaptedPerson> getPatients() {
        return this.patients;
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedPerson p : patients) {
            Patient patient = p.toModelType();
            if (addressBook.hasPerson(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(patient);
        }
        for (XmlAdaptedMedicine m : medicines) {
            Medicine medicine = m.toModelType();
            if (addressBook.hasMedicine(medicine)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDICINE);
            }
            addressBook.addMedicine(medicine);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return patients.equals(((XmlSerializableAddressBook) other).patients);
    }
}
