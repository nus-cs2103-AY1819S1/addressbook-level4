package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly adapted version of the Username.
 */
public class XmlAdaptedUsername {
    @XmlValue
    private String userName;

    /**
     * Constructs an XmlAdapterUsername.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUsername() {
    }

    /**
     * Converts a given Username into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedUsername(Username source) {
        userName = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Username toModelType() throws IllegalValueException {
        if (!Username.isValidName(userName)) {
            throw new IllegalValueException(Username.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Username(userName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUsername)) {
            return false;
        }

        return userName.equals(((XmlAdaptedUsername) other).userName);
    }
}
