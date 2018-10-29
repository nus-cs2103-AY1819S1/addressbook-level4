package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wish.SavedAmount;

/**
 * JAXB-friendly adapted version of the SavedAmount.
 */
public class XmlAdaptedSavedAmount {

    @XmlValue
    private String savedAmount;

    /**
     * Constructs an XmlAdaptedSavedAmount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSavedAmount() {}

    /**
     * Constructs a {@code XmlAdaptedSavedAmount} with the given {@code amount}.
     */
    public XmlAdaptedSavedAmount(String amount) {
        this.savedAmount = amount;
    }

    /**
     * Converts a given SavedAmount into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSavedAmount(SavedAmount source) {
        this.savedAmount = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the wish's SavedAmount object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wish
     */
    public SavedAmount toSavedAmountType() throws IllegalValueException {
        if (!SavedAmount.isValidSavedAmount(savedAmount)) {
            throw new IllegalValueException(SavedAmount.MESSAGE_SAVED_AMOUNT_INVALID);
        }
        return new SavedAmount(savedAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSavedAmount)) {
            return false;
        }

        return savedAmount.equals(((XmlAdaptedSavedAmount) other).savedAmount);
    }


}
