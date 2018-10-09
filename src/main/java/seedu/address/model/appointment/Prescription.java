package seedu.address.model.appointment;

import java.util.Date;

/**
 * Represents an Prescription in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Prescription {
    private String medicineName;
    private Date expirationDate;
    private int consumptionPerDay;
    private int amountToConsume;

    public Prescription(String medicineName, Date expirationDate, int consumptionPerDay, int amountToConsume) {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.consumptionPerDay = consumptionPerDay;
        this.amountToConsume = amountToConsume;
    }

    // Get Methods
    public String getMedicineName() {
        return medicineName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getConsumptionPerDay() {
        return consumptionPerDay;
    }

    public int getAmountToConsume() {
        return amountToConsume;
    }
}
