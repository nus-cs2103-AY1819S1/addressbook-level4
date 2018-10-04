package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Label;

/**
 * JAXB-friendly adapted version of the Label.
 */
public class XmlAdaptedLabel {

    @XmlValue
    private String labelName;

    /**
     * Constructs an XmlAdaptedLabel.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLabel() {}

    /**
     * Constructs a {@code XmlAdaptedLabel} with the given {@code labelName}.
     */
    public XmlAdaptedLabel(String labelName) {
        this.labelName = labelName;
    }

    /**
     * Converts a given Label into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLabel(Label source) {
        labelName = source.labelName;
    }

    /**
     * Converts this jaxb-friendly adapted label object into the model's Label object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Label toModelType() throws IllegalValueException {
        if (!Label.isValidLabelName(labelName)) {
            throw new IllegalValueException(Label.MESSAGE_LABEL_CONSTRAINTS);
        }
        return new Label(labelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLabel)) {
            return false;
        }

        return labelName.equals(((XmlAdaptedLabel) other).labelName);
    }
}
