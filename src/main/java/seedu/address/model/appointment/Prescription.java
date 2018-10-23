package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents an Prescription in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Prescription {
    private String medicineName;
    private LocalDateTime expirationDate;
    private int consumptionPerDay;
    private int amountToConsume;

    public Prescription(String medicineName, LocalDateTime expirationDate, int consumptionPerDay, int amountToConsume) {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.consumptionPerDay = consumptionPerDay;
        this.amountToConsume = amountToConsume;
    }

    // Get Methods
    public String getMedicineName() {
        return medicineName;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getConsumptionPerDay() {
        return consumptionPerDay;
    }

    public int getAmountToConsume() {
        return amountToConsume;
    }
}
