package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly adapted version of the Username.
 */
public class XmlAdaptedUsername {

    @XmlValue
    private String username;

    /**
     * Constructs an XmlAdaptedUsername.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUsername() {}

    /**
     * Constructs a {@code XmlAdaptedUsername} with the given {@code username}.
     */
    public XmlAdaptedUsername(String username) {
        this.username = username;
    }

    /**
     * Converts a given Username into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedUsername(Username source) {
        username = source.username;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Username object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant
     */
    public Username toModelType() throws IllegalValueException {
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(username);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUsername)) {
            return false;
        }

        return username.equals(((XmlAdaptedUsername) other).username);
    }
}
