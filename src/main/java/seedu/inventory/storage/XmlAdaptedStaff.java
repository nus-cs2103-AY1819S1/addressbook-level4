package seedu.inventory.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * @author darren96
 * JAXB-friendly version of Staff.
 */
public class XmlAdaptedStaff {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private Staff.Role role;

    /**
     * Constructs an XmlAdaptedStaff.
     * This is the default constructor that is required by JAXB.
     */
    public XmlAdaptedStaff() {}

    /**
     * Constructs an {@code XmlAdaptedStaff} with the given staff details.
     */
    public XmlAdaptedStaff(String username, String password, String name, Staff.Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    /**
     * Converts a given Staff into this class for JAXB user.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStaff
     */
    public XmlAdaptedStaff(Staff source) {
        this.username = source.getUsername().username;
        this.password = source.getPassword().password;
        this.name = source.getStaffName().fullName;
        this.role = source.getRole();
    }

    /**
     * Converts this JAXB-friendly adapted staff object into the model's Staff object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted staff
     */
    public Staff toModelType() throws IllegalValueException {
        if (this.username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(this.username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (this.password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(this.password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StaffName.class.getSimpleName()));
        }
        if (!StaffName.isValidName(this.name)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final StaffName modelStaffName = new StaffName(name);

        if (this.role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Staff.Role.class.getSimpleName()));
        }

        final Staff.Role modelRole = role;

        return new Staff(modelUsername, modelPassword, modelStaffName, modelRole);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof XmlAdaptedStaff)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        XmlAdaptedStaff otherStaff = (XmlAdaptedStaff) other;
        return Objects.equals(username, otherStaff.username)
                && Objects.equals(password, otherStaff.password)
                && Objects.equals(name, otherStaff.name)
                && role == otherStaff.role;
    }
}
