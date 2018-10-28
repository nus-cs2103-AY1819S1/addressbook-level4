package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;
import seedu.address.model.volunteer.VolunteerId;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    @XmlElement(required = true)
    private int eventId;
    @XmlElement(required = true)
    private int volunteerId;
    @XmlElement(required = true)
    private String hour;
    @XmlElement(required = true)
    private String remark;

    /**
     * Constructs an XmlAdaptedRecord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecord() {
    }

    /**
     * Constructs an {@code XmlAdaptedRecord} with the given record details.
     */
    public XmlAdaptedRecord(int eventId, int volunteerId, String hour, String remark) {
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.hour = hour;
        this.remark = remark;
    }

    /**
     * Converts a given Record into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecord
     */
    public XmlAdaptedRecord(Record source) {
        eventId = source.getEventId().id;
        volunteerId = source.getVolunteerId().id;
        hour = source.getHour().value;
        remark = source.getRemark().value;
    }

    /**
     * Converts this jaxb-friendly adapted record object into the model's Record object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record
     */
    public Record toModelType() throws IllegalValueException {
        if (!EventId.isValidId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_NAME_CONSTRAINTS);
        }
        final EventId modelEventId = new EventId(eventId);

        if (!VolunteerId.isValidId(volunteerId)) {
            throw new IllegalValueException(VolunteerId.MESSAGE_NAME_CONSTRAINTS);
        }
        final VolunteerId modelVolunteerId = new VolunteerId(volunteerId);

        if (hour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Hour.class.getSimpleName()));
        }
        if (!Hour.isValidHour(hour)) {
            throw new IllegalValueException(Hour.MESSAGE_HOUR_CONSTRAINTS);
        }
        final Hour modelHour = new Hour(hour);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        return new Record(modelEventId, modelVolunteerId, modelHour, modelRemark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecord)) {
            return false;
        }

        XmlAdaptedRecord otherRecord = (XmlAdaptedRecord) other;
        return Objects.equals(eventId, otherRecord.eventId)
                && Objects.equals(volunteerId, otherRecord.volunteerId)
                && Objects.equals(hour, otherRecord.hour)
                && Objects.equals(remark, otherRecord.remark);
    }
}
