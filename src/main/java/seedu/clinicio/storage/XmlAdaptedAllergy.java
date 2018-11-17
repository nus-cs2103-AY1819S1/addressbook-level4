package seedu.clinicio.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.patient.Allergy;

/**
 * JAXB-friendly adapted version of the Allergy.
 */
public class XmlAdaptedAllergy {

    @XmlValue
    private String allergy;

    /**
     * Constructs an XmlAdaptedAllergy.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAllergy() {}

    /**
     * Constructs a {@code XmlAdaptedAllergy} with the given {@code allergy}.
     */
    public XmlAdaptedAllergy(String allergy) {
        this.allergy = allergy;
    }

    /**
     * Converts a given Allergy into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAllergy(Allergy source) {
        allergy = source.allergy;
    }

    /**
     * Converts this jaxb-friendly adapted allergy object into the model's Allergy object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public Allergy toModelType() throws IllegalValueException {
        if (!Allergy.isValidAllergy(allergy)) {
            throw new IllegalValueException(Allergy.MESSAGE_ALLERGY_CONSTRAINTS);
        }
        return new Allergy(allergy);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAllergy)) {
            return false;
        }

        return allergy.equals(((XmlAdaptedAllergy) other).allergy);
    }
}
