package seedu.address.storage;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.user.Email;
import seedu.address.model.user.Name;
import seedu.address.model.user.Password;
import seedu.address.model.user.Phone;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly version of the User.
 */
public class XmlAdaptedUser {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "user's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;

    /**
     * Constructs an XmlAdaptedRestaurant.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs an {@code XmlAdaptedRestaurant} with the given restaurant details.
     */
    public XmlAdaptedUser(String username, String password, String name, String phone, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdadptedUser
     */
    public XmlAdaptedUser(User source) {
        username = source.getUsername().username;
        password = source.getPassword().password;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
    }

    /**
     * Converts this jaxb-friendly adapted restaurant object into the model's Restaurant object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant
     */
    public User toModelType() throws IllegalValueException {
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        return new User(modelUsername, modelPassword, modelName, modelPhone, modelEmail);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        XmlAdaptedUser otherUser = (XmlAdaptedUser) other;
        return Objects.equals(username, otherUser.username)
                && Objects.equals(password, otherUser.password)
                && Objects.equals(name, otherUser.name)
                && Objects.equals(phone, otherUser.phone)
                && Objects.equals(email, otherUser.email);
    }
}
