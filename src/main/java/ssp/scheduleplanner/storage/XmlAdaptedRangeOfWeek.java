package ssp.scheduleplanner.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import ssp.scheduleplanner.commons.exceptions.IllegalValueException;
import ssp.scheduleplanner.model.rangeofweek.RangeOfWeek;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.model.task.Priority;

/**
 * JAXB-friendly version of the rangeOfWeek.
 */
public class XmlAdaptedRangeOfWeek {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "RangeOfWeek's %s field is missing!";

    @XmlElement(required = true)
    private String startOfWeekDate;
    @XmlElement(required = true)
    private String endOfWeekDate;
    @XmlElement(required = true)
    private String description;

    /**
     * Constructs an XmlAdaptedRangeOfWeek.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRangeOfWeek() {}

    /**
     * Constructs an {@code XmlAdaptedRangeOfWeek} with the given task details.
     */
    public XmlAdaptedRangeOfWeek(String startOfWeekDate, String endOfWeekDate, String description) {
        this.startOfWeekDate = startOfWeekDate;
        this.endOfWeekDate = endOfWeekDate;
        this.description = description;
    }

    /**
     * Converts a given RangeOfWeek into this class for JAXB use.
     * @param source future changes to this will not affect the created XmlAdaptedRangeOfWeek
     */
    public XmlAdaptedRangeOfWeek(RangeOfWeek source) {
        startOfWeekDate = source.getStartOfWeekDate();
        endOfWeekDate = source.getEndOfWeekDate();
        description = source.getDescription();
    }

    /**
     * Converts this jaxb-friendly adapted rangeOfWeek object into the model's rangeOfWeek object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public RangeOfWeek toModelType() throws IllegalValueException {
        if (startOfWeekDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RangeOfWeek.class.getSimpleName()));
        }
        if (!Date.isValidDate(startOfWeekDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }

        if (endOfWeekDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RangeOfWeek.class.getSimpleName()));
        }

        if (!Date.isValidDate(endOfWeekDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RangeOfWeek.class.getSimpleName()));
        }

        return new RangeOfWeek(startOfWeekDate, endOfWeekDate, description);
    }

    public String getStartOfWeekDate() {
        return startOfWeekDate;
    }

    public String getEndOfWeekDate() {
        return endOfWeekDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(startOfWeekDate);
        sb.append(endOfWeekDate);
        sb.append(description);
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRangeOfWeek)) {
            return false;
        }

        XmlAdaptedRangeOfWeek otherRangeOfWeek = (XmlAdaptedRangeOfWeek) other;
        return Objects.equals(startOfWeekDate, otherRangeOfWeek.startOfWeekDate)
                && Objects.equals(endOfWeekDate, otherRangeOfWeek.endOfWeekDate)
                && Objects.equals(description, otherRangeOfWeek.description);
    }
}
