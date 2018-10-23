package seedu.address.storage;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Prescription;

/**
 * JAXB-friendly adapted version of the Prescription.
 */
public class XmlAdaptedPrescription {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Prescription's %s field is missing!";

    @XmlValue
    private String medicineName;
    @XmlValue
    private String expirationDate;
    @XmlValue
    private int consumptionPerDay;
    @XmlValue
    private int amountToConsume;

    /**
     * Constructs an XmlAdaptedPrescription.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPrescription() {}

    /**
     * Constructs a {@code XmlAdaptedPrescription} with the given prescription details
     */
    public XmlAdaptedPrescription(String medicineName, String expirationDate,
                                  int consumptionPerDay, int amountToConsume) {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.consumptionPerDay = consumptionPerDay;
        this.amountToConsume = amountToConsume;
    }

    /**
     * Converts a given Prescription into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPrescription(Prescription source) {
        medicineName = source.getMedicineName();
        expirationDate = source.getExpirationDate().toString();
        consumptionPerDay = source.getConsumptionPerDay();
        amountToConsume = source.getAmountToConsume();
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
        final String medicineName = "Empty";

        if (expirationDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final String expirationDate = "2018-01-01T00:00:00";
        LocalDateTime expirationInLocalDateTime = LocalDateTime.parse(expirationDate);

        if (consumptionPerDay == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }
        final int consumptionPerDay = 1;

        if (amountToConsume == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }
        final int amountToConsume = 1;

        return new Prescription(medicineName, expirationInLocalDateTime, consumptionPerDay, amountToConsume);
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getConsumptionPerDay() {
        return consumptionPerDay;
    }

    public int getAmountToConsume() {
        return amountToConsume;
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
                && expirationDate.equals(((XmlAdaptedPrescription) other).getExpirationDate())
                && consumptionPerDay == (((XmlAdaptedPrescription) other).getConsumptionPerDay())) {
            return true;
        } else {
            return false;
        }
    }
}
