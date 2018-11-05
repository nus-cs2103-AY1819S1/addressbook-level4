package seedu.address.testutil;

import seedu.address.model.appointment.ConsumptionPerDay;
import seedu.address.model.appointment.Dosage;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;

/**
 * A utility class to help with building Prescription objects
 */
public class PrescriptionBuilder {
    public static final int DEFAULT_APPOINTMENT_ID = 10000;
    public static final String DEFAULT_MEDICINE_NAME = "Paracetamol";
    public static final String DEFAULT_DOSAGE = "2";
    public static final String DEFAULT_CONSUMPTION_PER_DAY = "3";

    private int appointmentId;
    private MedicineName medicineName;
    private Dosage dosage;
    private ConsumptionPerDay consumptionPerDay;

    /**
     * Default constructor for the PrescriptionBuilder
     */
    public PrescriptionBuilder() {
        appointmentId = DEFAULT_APPOINTMENT_ID;
        medicineName = new MedicineName(DEFAULT_MEDICINE_NAME);
        dosage = new Dosage(DEFAULT_DOSAGE);
        consumptionPerDay = new ConsumptionPerDay(DEFAULT_CONSUMPTION_PER_DAY);
    }

    /**
     * Initializes the PrescriptionBuilder with the data of {@code prescriptionToCopy}
     */
    public PrescriptionBuilder(Prescription prescriptionToCopy) {
        appointmentId = prescriptionToCopy.getId();
        medicineName = prescriptionToCopy.getMedicineName();
        dosage = prescriptionToCopy.getDosage();
        consumptionPerDay = prescriptionToCopy.getConsumptionPerDay();
    }

    /**
     * Sets the {@code appointmentId} of the {@code Prescription} that we are building
     */
    public PrescriptionBuilder withAppointmentId(int id) {
        this.appointmentId = id;
        return this;
    }

    /**
     * Sets the {@code medicineName} of the {@code Prescription} that we are building
     */
    public PrescriptionBuilder withMedicineName(String medicineName) {
        this.medicineName = new MedicineName(medicineName);
        return this;
    }

    /**
     * Sets the {@code dosage} of the {@code Prescription} that we are building
     */
    public PrescriptionBuilder withDosage(String dosage) {
        this.dosage = new Dosage(dosage);
        return this;
    }

    /**
     * Sets the {@code consumptionPerDay} of the {@code Prescription} that we are building
     */
    public PrescriptionBuilder withConsumptionPerDay(String consumptionPerDay) {
        this.consumptionPerDay = new ConsumptionPerDay(consumptionPerDay);
        return this;
    }

    public Prescription build() {
        return new Prescription(appointmentId, medicineName, dosage, consumptionPerDay);
    }
}
