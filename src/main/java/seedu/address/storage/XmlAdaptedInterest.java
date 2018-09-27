package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interest.Interest;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedInterest {

    @XmlValue
    private String interestName;

    /**
     * Constructs an XmlAdaptedInterest.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedInterest() {}

    /**
     * Constructs a {@code XmlAdaptedInterest} with the given {@code interestName}.
     */
    public XmlAdaptedInterest(String interestName) {
        this.interestName = interestName;
    }

    /**
     * Converts a given Interest into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedInterest(Interest source) {
        interestName = source.interestName;
    }

    /**
     * Converts this jaxb-friendly adapted interest object into the model's Interest object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Interest toModelType() throws IllegalValueException {
        if (!Interest.isValidInterestName(interestName)) {
            throw new IllegalValueException(Interest.MESSAGE_INTEREST_CONSTRAINTS);
        }
        return new Interest(interestName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedInterest)) {
            return false;
        }

        return interestName.equals(((XmlAdaptedInterest) other).interestName);
    }
}
