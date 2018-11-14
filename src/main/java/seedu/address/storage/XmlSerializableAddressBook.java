package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignment list contains duplicate assignment(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedAssignment> assignments;
    @XmlElement
    private List<XmlAdaptedAttendance> attendance;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        assignments = new ArrayList<>();
        attendance = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        List<String> uidList = new ArrayList<>();
        for (Assignment a: src.getAssignmentList()) {
            assignments.add(new XmlAdaptedAssignment(a));
            uidList.add(a.getUniqueId());
        }
        List<String> attendanceUidList = new ArrayList<>();
        for (Attendance a: src.getAttendanceList()) {
            attendance.add(new XmlAdaptedAttendance(a));
            attendanceUidList.add(a.getUniqueId());
        }
        for (Person p: src.getPersonList()) {
            persons.add(new XmlAdaptedPerson(p, uidList, attendanceUidList));
        }
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
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
        for (XmlAdaptedAssignment a : assignments) {
            Assignment assignment = a.toModelType();
            if (addressBook.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }
            addressBook.addAssignment(assignment);
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
        return persons.equals(((XmlSerializableAddressBook) other).persons);
    }
}
