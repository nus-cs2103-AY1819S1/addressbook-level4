package seedu.clinicio.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.patient.Medication;

/**
 * JAXB-friendly adapted version of the Medications.
 */
public class XmlAdaptedMedication {

    @XmlValue
    private String medication;

    /**
     * Constructs an XmlAdaptedMedication.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedication() {}

    /**
     * Constructs a {@code XmlAdaptedMedication} with the given {@code medication}.
     */
    public XmlAdaptedMedication(String medication) {
        this.medication = medication;
    }

    /**
     * Converts a given Medication into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMedication(Medication source) {
        medication = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted medication object into the model's Medication object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public Medication toModelType() throws IllegalValueException {
        if (!Medication.isValidMed(medication)) {
            throw new IllegalValueException(Medication.MESSAGE_MED_CONSTRAINTS);
        }
        return new Medication(medication);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedication)) {
            return false;
        }

        return medication.equals(((XmlAdaptedMedication) other).medication);
    }
}
