package seedu.clinicio.storage;

import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;

//@@author jjlee050
/**
 * JAXB-friendly version of the Staff.
 */
public class XmlAdaptedStaff {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";

    @XmlElement(required = true)
    private String role;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedStaff. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedStaff() {
    }

    /**
     * Constructs an {@code XmlAdaptedStaff} with the given staff details.
     */
    public XmlAdaptedStaff(String role, String name, String password) {
        this.role = role;
        this.name = name;
        this.password = password;
    }

    /**
     * Converts a given Staff into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStaff
     */
    public XmlAdaptedStaff(Staff source) {
        role = source.getRole().name();
        name = source.getName().fullName;
        password = source.getPassword().password;
    }

    /**
     * Converts this jaxb-friendly adapted staff object into the model's Staff object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted staff
     */
    public Staff toModelType() throws IllegalValueException {
        Role modelRole;
        if (role == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        } else if (role.equals(DOCTOR.name())) {
            modelRole = DOCTOR;
        } else {
            modelRole = RECEPTIONIST;
        }

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

        return new Staff(modelRole, modelName, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedStaff)) {
            return false;
        }

        XmlAdaptedStaff otherPerson = (XmlAdaptedStaff) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(password, otherPerson.password);
    }
}
