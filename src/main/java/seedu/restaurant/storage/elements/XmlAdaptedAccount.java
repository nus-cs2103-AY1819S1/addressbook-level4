package seedu.restaurant.storage.elements;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Name;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai

/**
 * JAXB-friendly version of the {@code Account}.
 */
public class XmlAdaptedAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedAccount. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}

    /**
     * Constructs an {@code XmlAdaptedAccount} with the given account details.
     */
    public XmlAdaptedAccount(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    /**
     * Converts a given Account into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAccount
     */
    public XmlAdaptedAccount(Account source) {
        username = source.getUsername().toString();
        password = source.getPassword().toString();
        name = source.getName().toString();
    }

    /**
     * Verifies if the {@code Username} is valid.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    private void verifyUsername() throws IllegalValueException {
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINT);
        }
    }

    /**
     * Verifies if the {@code Password} is valid.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    private void verifyPassword() throws IllegalValueException {
        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINT);
        }
    }

    /**
     * Verifies if the {@code Name} is valid.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    private void verifyName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
    }

    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    public Account toModelType() throws IllegalValueException {

        verifyUsername();
        final Username modelUsername = new Username(username);

        verifyPassword();
        final Password modelPassword = new Password(password);

        verifyName();
        final Name modelName = new Name(name);

        return new Account(modelUsername, modelPassword, modelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAccount)) {
            return false;
        }

        XmlAdaptedAccount otherAccount = (XmlAdaptedAccount) other;
        return Objects.equals(username, otherAccount.username)
                && Objects.equals(password, otherAccount.password)
                && Objects.equals(name, otherAccount.name);
    }
}
