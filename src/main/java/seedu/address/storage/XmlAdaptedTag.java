package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Label;

/**
 * JAXB-friendly adapted version of the Label.
 */
public class XmlAdaptedTag {

    @XmlValue
    private String tagName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {
    }

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code labelName}.
     */
    public XmlAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given Label into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Label source) {
        tagName = source.labelName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Label object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Label toModelType() throws IllegalValueException {
        if (!Label.isValidLabelName(tagName)) {
            throw new IllegalValueException(Label.MESSAGE_LABEL_CONSTRAINTS);
        }
        return new Label(tagName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        return tagName.equals(((XmlAdaptedTag) other).tagName);
    }
}
