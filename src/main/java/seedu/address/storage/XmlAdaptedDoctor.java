package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Id;
import seedu.address.model.doctor.Password;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

//@@author jjlee050

/**
 * JAXB-friendly version of the Doctor.
 */
public class XmlAdaptedDoctor {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Doctor's %s field is missing!";

    @XmlElement(required = true)
    private int id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedDoctor. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDoctor() {
    }

    /**
     * Constructs an {@code XmlAdaptedDoctor} with the given doctor details.
     */
    public XmlAdaptedDoctor(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     * Converts a given Doctor into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDoctor
     */
    public XmlAdaptedDoctor(Doctor source) {
        id = source.getId().id;
        name = source.getName().fullName;
        password = source.getPassword().password;
    }

    /**
     * Converts this jaxb-friendly adapted doctor object into the model's Doctor object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted doctor
     */
    public Doctor toModelType() throws IllegalValueException {
        if (id < 1) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Id.isValidId(id)) {
            throw new IllegalValueException(Id.MESSAGE_ID_CONSTRAINTS);
        }
        final Id modelId = new Id(id);

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
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        return new Doctor(modelId, modelName, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDoctor)) {
            return false;
        }

        XmlAdaptedDoctor otherPerson = (XmlAdaptedDoctor) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(id, otherPerson.id)
                && Objects.equals(password, otherPerson.password);
    }
}
