package seedu.address.model.medicalhistory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Timestamp class used in the {@code Diagnosis} class.
 * Provides a timestamp of when a diagnosis was created.
 */
public class Timestamp {
    private final String timestamp;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

    /**
     * Constructs a timestamp for the medical records.
     */
    Timestamp() {
        timestamp = LocalDateTime.now().format(formatter);
    }

    /**
     * Getter method for the timestamp, whenever it was called.
     *
     * @return the timestamp generated at the time whenever the constructor was called.
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    public DateTimeFormatter getFormatter() {
        return this.formatter;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timestamp)) {
            return false;
        }

        return timestamp.equals(((Timestamp) other).getTimestamp());
    }
}
