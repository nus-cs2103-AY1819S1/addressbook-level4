package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ArchiveList;
import seedu.address.model.ReadOnlyArchiveList;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "archivelist")
public class XmlSerializableArchiveList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableArchiveList() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableArchiveList(ReadOnlyArchiveList src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public ArchiveList toModelType() throws IllegalValueException {
        ArchiveList archiveList = new ArchiveList();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (archiveList.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            archiveList.addPerson(person);
        }
        return archiveList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableArchiveList)) {
            return false;
        }
        return persons.equals(((XmlSerializableArchiveList) other).persons);
    }
}
