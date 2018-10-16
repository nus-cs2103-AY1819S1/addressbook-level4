package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Mark;

import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly adapted version of the Mark.
 */
public class XmlAdaptedMark {

    @XmlElement
    private String key;
    @XmlElement
    private String value;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMark() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedMark(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMark(String key, Mark source) {
        this.key = key;
        this.value = source.internalString;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Mark toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(tagName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMark)) {
            return false;
        }

        return tagName.equals(((XmlAdaptedMark) other).tagName);
    }
}
