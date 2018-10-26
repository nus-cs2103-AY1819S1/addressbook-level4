package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;
import seedu.address.model.receptionist.Receptionist;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_DOCTOR = "Doctors list contains duplicate doctor(s).";
    public static final String MESSAGE_DUPLICATE_RECEPTIONIST = "Doctors list contains duplicate receptionist(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedDoctor> doctors;

    @XmlElement
    private List<XmlAdaptedReceptionist> receptionists;
    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        doctors = new ArrayList<>();
        receptionists = new ArrayList<>(); 
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        doctors.addAll(src.getDoctorList().stream().map(XmlAdaptedDoctor::new).collect(Collectors.toList()));
        receptionists.addAll(src.getReceptionistList().stream().map(XmlAdaptedReceptionist::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} & {@code XmlAdaptedDoctor} & & {@code XmlAdaptedReceptionist}. 
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        //@@author jjlee050
        for (XmlAdaptedDoctor d : doctors) {
            Doctor doctor = d.toModelType();
            if (addressBook.hasDoctor(doctor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DOCTOR);
            }
            addressBook.addDoctor(doctor);
        }

        for (XmlAdaptedReceptionist r : receptionists) {
            Receptionist receptionist = r.toModelType();
            if (addressBook.hasReceptionist(receptionist)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECEPTIONIST);
            }
            addressBook.addReceptionist(receptionist);
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
        return persons.equals(((XmlSerializableAddressBook) other).persons)
                && doctors.equals(((XmlSerializableAddressBook) other).doctors)
                && receptionists.equals(((XmlSerializableAddressBook) other).receptionists);
    }
}
