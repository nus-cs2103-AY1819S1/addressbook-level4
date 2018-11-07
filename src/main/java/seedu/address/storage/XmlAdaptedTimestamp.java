package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.Timestamp;

/**
 * A JAXB-ready version of Timestamp
 */
@XmlRootElement
public class XmlAdaptedTimestamp {
    @XmlElement(required = true)
    private String timestampAsString;

    /**
     * Empty constructor as required by JAXB.
     */
    public XmlAdaptedTimestamp(){}

    /**
     * Constructs a JAXB-friendly adapted Timestamp using a string
     * that follows {@code Timestamp.DATE_TIME_FORMATTER_PATTERN}
     */
    public XmlAdaptedTimestamp(String timestampAsString) throws IllegalValueException {
        try {
            LocalDateTime.parse(timestampAsString, Timestamp.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Timestamp.DATE_TIME_FORMATTER_PATTERN, e);
        }

        this.timestampAsString = timestampAsString;
    }

    public XmlAdaptedTimestamp(Timestamp source) {
        this.timestampAsString = source.toString();
    }

    /**
     * Converts this JAXB-friendly adapted Timestamp object into the model's Timestamp object.
     *
     * @throws IllegalValueException if {@code timestampAsString} failed to be parsed into a Timestamp object.
     */
    public Timestamp toModelType() {
        return new Timestamp(timestampAsString);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof XmlAdaptedTimestamp)) {
            return false;
        }

        XmlAdaptedTimestamp xat = (XmlAdaptedTimestamp) o;
        return this.timestampAsString.equals(xat.timestampAsString);
    }
}
