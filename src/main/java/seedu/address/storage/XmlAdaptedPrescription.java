package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Prescription;

//@@author snajef
/**
 * JAXB-friendly adapted version of the Prescription class.
 */

@XmlRootElement
public class XmlAdaptedPrescription {
    public static final String MESSAGE_END_DATE_MUST_BE_AFTER_START_DATE = "End date must be after start date!";

    @XmlElement(required = true)
    private String drugName;

    @XmlElement(required = true)
    private XmlAdaptedDose dose;

    @XmlElement(required = true)
    private XmlAdaptedDuration duration;

    /**
     * Constructs an XmlAdaptedPrescription.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPrescription() {}

    /**
     * Constructs a {@code XmlAdaptedPrescription} with the given details.
     */
    public XmlAdaptedPrescription(String drugName, double dosage, String doseUnit, int dosesPerDay, String startDate,
        String endDate, int durationInDays) throws IllegalValueException {

        this.drugName = drugName;
        dose = new XmlAdaptedDose(dosage, doseUnit, dosesPerDay);
        duration = new XmlAdaptedDuration(startDate, endDate, durationInDays);
    }

    /**
     * Converts a given Prescription into this class for JAXB use.
     */
    public XmlAdaptedPrescription(Prescription source) {
        drugName = source.getDrugName();
        dose = new XmlAdaptedDose(source.getDose());
        duration = new XmlAdaptedDuration(source.getDuration());
    }

    /**
     * Converts this JAXB-friendly adapted Prescription object into the model's Prescription object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Prescription.
     */
    public Prescription toModelType() throws IllegalValueException {
        return new Prescription(drugName, dose.toModelType(), duration.toModelType());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPrescription)) {
            return false;
        }

        XmlAdaptedPrescription xap = (XmlAdaptedPrescription) other;
        return drugName.equals(xap.drugName)
            && dose.equals(xap.dose)
            && duration.equals(xap.duration);
    }
}
