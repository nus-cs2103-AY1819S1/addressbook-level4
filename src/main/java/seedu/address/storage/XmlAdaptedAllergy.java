package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;

/**
 * JAXB-friendly adapted version of the Allergy.
 */
public class XmlAdaptedAllergy {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Allergy's %s field is missing!";

    @XmlValue
    String allergy;

    /**
     * Constructs an XmlAdaptedAllergy.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAllergy(){}

    /**
     * Constructs an XmlAdaptedAllergy.
     * The argument is String.
     */
    public XmlAdaptedAllergy(String allergy) {
        this.allergy = allergy;
    }

    /**
     * Constructs an XmlAdaptedAllergy.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAllergy(Allergy source) {
        allergy = source.allergy;
    }



    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
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

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        return allergy.equals(((XmlAdaptedAllergy) other).allergy);
    }

}
