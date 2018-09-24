package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.record.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;
import seedu.address.model.record.VolunteerId;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    @XmlElement(required = true)
    private String eventId;
    @XmlElement(required = true)
    private String volunteerId;
    @XmlElement(required = true)
    private String hour;
    @XmlElement
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
    public XmlAdaptedRecord(String eventId, String volunteerId, String hour, String remark) {
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.hour = hour;
        if (!remark.equals("")) {
            this.remark = remark;
        }
    }

    /**
     * Converts a given Record into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecord
     */
    public XmlAdaptedRecord(Record source) {
        eventId = source.getEventId().value;
        volunteerId = source.getVolunteerId().value;
        hour = source.getHour().value;
        remark = source.getRemark().value;
    }

    /**
     * Converts this jaxb-friendly adapted record object into the model's Record object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record
     */
    public Record toModelType() throws IllegalValueException {
        if (eventId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EventId.class.getSimpleName()));
        }
        // TODO: To add the validation for eventId once class is replaced and replace EventId with valid class
        final EventId modelEventId = new EventId(eventId);

        if (volunteerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, VolunteerId.class
                    .getSimpleName()));
        }
        // TODO: To add the validation for volunteerId once class is replaced and replace EventId with valid class
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
