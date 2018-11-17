package seedu.clinicio.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.patient.MedicalProblem;

/**
 * JAXB-friendly adapted version of the MedicalProblem.
 */
public class XmlAdaptedMedicalProblem {

    @XmlValue
    private String medicalProblem;

    /**
     * Constructs an XmlAdaptedMedicalProblem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedicalProblem() {}

    /**
     * Constructs a {@code XmlAdaptedMedicalProblem} with the given {@code medicalProblem}.
     */
    public XmlAdaptedMedicalProblem(String medicalProblem) {
        this.medicalProblem = medicalProblem;
    }

    /**
     * Converts a given MedicalProblem into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMedicalProblem(MedicalProblem source) {
        medicalProblem = source.medProb;
    }

    /**
     * Converts this jaxb-friendly adapted medical problem object into the model's MedicalProblem object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public MedicalProblem toModelType() throws IllegalValueException {
        if (!MedicalProblem.isValidMedProb(medicalProblem)) {
            throw new IllegalValueException(MedicalProblem.MESSAGE_MED_PROB_CONSTRAINTS);
        }
        return new MedicalProblem(medicalProblem);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedicalProblem)) {
            return false;
        }

        return medicalProblem.equals(((XmlAdaptedMedicalProblem) other).medicalProblem);
    }
}
