package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.medicalrecord.Disease;

/**
 * JAXB-friendly adapted version of the Disease.
 */
public class XmlAdaptedDisease {

    @XmlValue
    private String diseaseName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDisease() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedDisease(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedDisease(Disease source) {
        diseaseName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public Disease toModelType() throws IllegalValueException {
        if (!Disease.isValidDisease(diseaseName)) {
            throw new IllegalValueException(Disease.MESSAGE_DISEASE_CONSTRAINTS);
        }
        return new Disease(diseaseName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDisease)) {
            return false;
        }

        return diseaseName.equals(((XmlAdaptedDisease) other).diseaseName);
    }
}
