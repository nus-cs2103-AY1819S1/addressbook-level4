package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.medicalrecord.DrugAllergy;
import seedu.address.model.tag.Tag;

public class XmlAdaptedDrugAllergy {

    @XmlValue
    private String drugAllergyName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDrugAllergy() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedDrugAllergy(String drugAllergyName) {
        this.drugAllergyName = drugAllergyName;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedDrugAllergy(DrugAllergy source) {
        drugAllergyName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public DrugAllergy toModelType() throws IllegalValueException {
        if (!DrugAllergy.isValidDrugAllergy(drugAllergyName)) {
            throw new IllegalValueException(DrugAllergy.MESSAGE_DRUGALLERGY_CONSTRAINTS);
        }
        return new DrugAllergy(drugAllergyName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDrugAllergy)) {
            return false;
        }

        return drugAllergyName.equals(((XmlAdaptedDrugAllergy) other).drugAllergyName);
    }
}
