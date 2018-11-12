package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySsenisub;
import seedu.address.model.Ssenisub;
import seedu.address.model.person.Person;

/**
 * An Immutable Ssenisub that is serializable to XML format
 */
@XmlRootElement(name = "ssenisub")
public class XmlSerializableSsenisub {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializableSsenisub.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSsenisub() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSsenisub(ReadOnlySsenisub src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Ssenisub into the model's {@code Ssenisub} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public Ssenisub toModelType() throws IllegalValueException {
        Ssenisub ssenisub = new Ssenisub();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (ssenisub.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            ssenisub.addPerson(person);
        }
        return ssenisub;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSsenisub)) {
            return false;
        }
        return persons.equals(((XmlSerializableSsenisub) other).persons);
    }
}
