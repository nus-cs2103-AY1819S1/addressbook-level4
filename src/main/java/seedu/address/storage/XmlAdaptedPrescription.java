package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.AppointmentId;
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
    private int appointmentId;
    @XmlElement(required = true)
    private String medicineName;
    @XmlElement(required = true)
    private String dosage;
    @XmlElement(required = true)
    private String consumptionPerDay;

    /**
     * Constructs an XmlAdaptedPrescription.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPrescription() {}

    /**
     * Constructs a {@code XmlAdaptedPrescription} with the given prescription details
     */
    public XmlAdaptedPrescription(int appointmentId, String medicineName, String dosage, String consumptionPerDay) {
        this.appointmentId = appointmentId;
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
        appointmentId = source.getId();
        medicineName = source.getMedicineName().getFullMedicineName();
        dosage = source.getDosage().getValue();
        consumptionPerDay = source.getConsumptionPerDay().getValue();
    }

    /**
     * Converts this jaxb-friendly adapted Prescription object into the model's Prescription object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Prescription toModelType() throws IllegalValueException {

        if (appointmentId == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentId.class.getSimpleName()));
        }
        final int medAppointmentId = appointmentId;

        if (medicineName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicineName.class.getSimpleName()));
        }
        final MedicineName medName = new MedicineName(medicineName);


        if (dosage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Dosage.class.getSimpleName()));
        }
        final Dosage medDosage = new Dosage(dosage);

        if (consumptionPerDay == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ConsumptionPerDay.class.getSimpleName()));
        }
        final ConsumptionPerDay medConsumptionPerDay = new ConsumptionPerDay(consumptionPerDay);

        return new Prescription(medAppointmentId, medName, medDosage, medConsumptionPerDay);
    }

    public int getAppointmentId() { return appointmentId; }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getConsumptionPerDay() {
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
