package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Mark;

/**
 * JAXB-friendly adapted version of the Mark.
 */
public class XmlAdaptedMark {

    @XmlElement
    private String key;
    @XmlElement
    private String value;

    /**
     * Constructs an XmlAdaptedMark.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMark() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code key}, {@code value}.
     */
    public XmlAdaptedMark(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Converts a given Mark into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMark(String key, Mark source) {
        this.key = key;
        this.value = source.internalString;
    }

    /**
     * Converts this jaxb-friendly adapted mark object into the model's Mark object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted mark
     */
    public Mark toModelType() throws IllegalValueException {
        if (!Mark.isValid(value)) {
            throw new IllegalValueException(Mark.MESSAGE_CONSTRAINTS);
        }
        return new Mark(value);
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMark)) {
            return false;
        }

        return value.equals(((XmlAdaptedMark) other).value)
                && key.equals(((XmlAdaptedMark) other).key);
    }
}
