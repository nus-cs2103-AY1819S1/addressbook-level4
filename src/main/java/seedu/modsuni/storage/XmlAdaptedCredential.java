package seedu.modsuni.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.Username;

/**
 * JAXB-friendly version of the Credential.
 */
public class XmlAdaptedCredential {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Credential's "
        + "%s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedCredential.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCredential() {}

    /**
     * Constructs an {@code XmlAdaptedCredential} with the given details.
     */
    public XmlAdaptedCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given Credential into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCredential
     */
    public XmlAdaptedCredential(Credential source) {
        username = source.getUsername().toString();
        password = source.getPassword().toString();
    }


    /**
     * Converts this jaxb-friendly adapted credential object into the model's
     * Credential object.
     *
     * @throws IllegalValueException if there were any data constraints
     * violated in the adapted credential
     */
    public Credential toModelType() throws IllegalValueException {

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Username"));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Password"));
        }

        return new Credential(
            new Username(username),
            new Password(password));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCredential)) {
            return false;
        }

        XmlAdaptedCredential otherCredential = (XmlAdaptedCredential) other;
        return Objects.equals(username, otherCredential.username)
            && Objects.equals(password, otherCredential.password);
    }
}
