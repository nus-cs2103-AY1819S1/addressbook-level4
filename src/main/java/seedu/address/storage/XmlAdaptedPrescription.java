package seedu.address.storage;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.ConsumptionPerDay;
import seedu.address.model.appointment.Dosage;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;

/**
 * JAXB-friendly adapted version of the Prescription.
 */
public class XmlAdaptedPrescription {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Prescription's %s field is missing!";

    @XmlElement(required = true)
    private MedicineName medicineName;
    @XmlElement(required = true)
    private Dosage dosage;
    @XmlElement(required = true)
    private ConsumptionPerDay consumptionPerDay;

    /**
     * Constructs an XmlAdaptedPrescription.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPrescription() {}

    /**
     * Constructs a {@code XmlAdaptedPrescription} with the given prescription details
     */
    public XmlAdaptedPrescription(MedicineName medicineName, Dosage dosage, ConsumptionPerDay consumptionPerDay) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.consumptionPerDay = consumptionPerDay;
    }

    /**
     * Converts a given Prescription into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPrescription(Prescription source) {
        medicineName = source.getMedicineName();
        dosage = source.getDosage();
        consumptionPerDay= source.getConsumptionPerDay();
    }

    /**
     * Converts this jaxb-friendly adapted Prescription object into the model's Prescription object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Prescription toModelType() throws IllegalValueException {


        if (medicineName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final MedicineName medicineName = new MedicineName("Empty");


        if (Integer.parseInt(dosage.toString()) == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }
        final Dosage dosage = new Dosage(String.valueOf(1));

        if (Integer.parseInt(consumptionPerDay.toString()) == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }
        final ConsumptionPerDay consumptionPerDay = new ConsumptionPerDay(String.valueOf(1));

        return new Prescription(12, medicineName, dosage, consumptionPerDay);
    }

    public MedicineName getMedicineName() {
        return medicineName;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public ConsumptionPerDay getConsumptionPerDay() {
        return consumptionPerDay;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPrescription)) {
            return false;
        }

        if (medicineName.equals(((XmlAdaptedPrescription) other).getMedicineName())
                && dosage.equals(((XmlAdaptedPrescription) other).getDosage())
                && consumptionPerDay == (((XmlAdaptedPrescription) other).getConsumptionPerDay())) {
            return true;
        } else {
            return false;
        }
    }
}
