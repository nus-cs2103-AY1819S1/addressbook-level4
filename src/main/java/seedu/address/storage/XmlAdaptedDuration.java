package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Duration;

//@@author snajef
/**
 * XML adapted version of the Duration class.
 * @author Darien Chong
 *
 */
@XmlRootElement
public class XmlAdaptedDuration {
    public static final String MESSAGE_END_DATE_MUST_BE_AFTER_START_DATE = "End date must be after start date!";
    public static final String MESSAGE_DATE_FORMAT_NOT_CORRECT = "The date format given was not in the form; "
        + Duration.DATE_FORMAT_PATTERN;
    public static final String MESSAGE_DURATION_IN_DAYS_DOES_NOT_MATCH_DATE_RANGE = "The duration in days did not"
        + " match the given date range.";

    @XmlElement(required = true)
    private String startDateStr;

    @XmlElement(required = true)
    private String endDateStr;

    @XmlElement(required = true)
    private int durationInDays;

    public XmlAdaptedDuration() {}

    public XmlAdaptedDuration(String startDateStr, String endDateStr, int durationInDays)
        throws IllegalValueException {

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(startDateStr, Duration.DATE_FORMAT);
            endDate = LocalDate.parse(endDateStr, Duration.DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_FORMAT_NOT_CORRECT, e);
        }

        if (startDate.compareTo(endDate) > 0) {
            throw new IllegalValueException(MESSAGE_END_DATE_MUST_BE_AFTER_START_DATE);
        }

        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;

        if (durationInDays <= 0) {
            throw new IllegalValueException(Duration.MESSAGE_DURATION_MUST_BE_POSITIVE);
        }

        long daysBetweenDates = endDate.toEpochDay() - startDate.toEpochDay() + 1;
        if (daysBetweenDates != durationInDays) {
            throw new IllegalValueException(MESSAGE_DURATION_IN_DAYS_DOES_NOT_MATCH_DATE_RANGE);
        }

        this.durationInDays = durationInDays;
    }

    public XmlAdaptedDuration(Duration source) {
        durationInDays = source.getDurationInDays();
        startDateStr = source.getStartDateAsString();
        endDateStr = source.getEndDateAsString();
    }

    /** Converts this {@code XmlAdaptedDuration} into its corresponding {@code Duration} object equivalent. */
    public Duration toModelType() throws IllegalValueException {
        Duration result = new Duration(durationInDays);
        LocalDate startDate;

        try {
            startDate = LocalDate.parse(startDateStr, Duration.DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_FORMAT_NOT_CORRECT, e);
        }

        result.shiftDateRange(startDate);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedDuration)) {
            return false;
        }

        XmlAdaptedDuration xad = (XmlAdaptedDuration) o;
        return durationInDays == xad.durationInDays
            && startDateStr.equals(xad.startDateStr)
            && endDateStr.equals(xad.endDateStr);
    }
}
