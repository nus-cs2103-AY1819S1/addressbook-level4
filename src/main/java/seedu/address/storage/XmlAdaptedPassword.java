package seedu.address.storage;

import java.util.Base64;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Password;

/**
 * JAXB-friendly adapted version of the Permission.
 */
public class XmlAdaptedPassword {

    @XmlElement(required = true)
    private String salt;

    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedPassword.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPassword() {
    }

    /**
     * Constructs a {@code XmlAdaptedPassword} with the given {@code salt} and {@code hash}.
     */
    public XmlAdaptedPassword(byte[] salt, String hash) {
        this.salt = Base64.getEncoder().encodeToString(salt);
        this.password = hash;
    }

    /**
     * Converts a given Permission into this class for JAXB use.
     *
     * @param password future changes to this will not affect the created
     */
    public XmlAdaptedPassword(Password password) {
        this.salt = Base64.getEncoder().encodeToString(password.salt);
        this.password = password.hash;
    }

    /**
     * Converts this jaxb-friendly adapted Password object into the model's Password object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Password toModelType() throws IllegalValueException {
        byte[] byteSalt = Base64.getDecoder().decode(salt);
        return new Password(byteSalt, password);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPassword)) {
            return false;
        }

        return salt.equals(((XmlAdaptedPassword) other).salt)
            && password.equals(((XmlAdaptedPassword) other).password);
    }
}
