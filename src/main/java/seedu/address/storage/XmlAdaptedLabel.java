package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedLabel {

    @XmlValue
    private String tagName;

    /**
     * Constructs an XmlAdaptedLabel.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLabel() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedLabel(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given Label into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLabel(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted label object into the model's Label object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
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

        if (!(other instanceof XmlAdaptedLabel)) {
            return false;
        }

        return tagName.equals(((XmlAdaptedLabel) other).tagName);
    }
}
