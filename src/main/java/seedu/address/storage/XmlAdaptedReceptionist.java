package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.doctor.Doctor;
import seedu.address.model.password.Password;
import seedu.address.model.person.Name;
import seedu.address.model.receptionist.Receptionist;

//@@author jjlee050

/**
 * JAXB-friendly version of the Receptionist.
 */
public class XmlAdaptedReceptionist {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Receptionist's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedReceptionist. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReceptionist() {
    }

    /**
     * Constructs an {@code XmlAdaptedReceptionist} with the given receptionist details.
     */
    public XmlAdaptedReceptionist(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Converts a given Receptionist into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReceptionist
     */
    public XmlAdaptedReceptionist(Receptionist source) {
        name = source.getName().fullName;
        password = source.getPassword().password;
    }

    /**
     * Converts this jaxb-friendly adapted receptionist object into the model's Receptionist object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted receptionist
     */
    public Receptionist toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (password == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName()));
        }
        if (!Password.isValidHashedPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password, true);

        return new Receptionist(modelName, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReceptionist)) {
            return false;
        }

        XmlAdaptedReceptionist otherPerson = (XmlAdaptedReceptionist) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(password, otherPerson.password);
    }
}
