package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWishBook;
import seedu.address.model.WishBook;
import seedu.address.model.person.Person;

/**
 * An Immutable WishBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableWishBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializableWishBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableWishBook() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableWishBook(ReadOnlyWishBook src) {
        this();
        persons.addAll(src.getWishList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code WishBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public WishBook toModelType() throws IllegalValueException {
        WishBook wishBook = new WishBook();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (wishBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            wishBook.addPerson(person);
        }
        return wishBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableWishBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableWishBook) other).persons);
    }
}
