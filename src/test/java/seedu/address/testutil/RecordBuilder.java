package seedu.address.testutil;

import seedu.address.model.record.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;
import seedu.address.model.record.VolunteerId;

/**
 * A utility class to help with building Person objects.
 */
public class RecordBuilder {
    public static final String DEFAULT_EVENTID = "1";
    public static final String DEFAULT_VOLUNTEERID = "1";
    public static final String DEFAULT_HOUR = "0";
    public static final String DEFAULT_REMARK = "Emcee for event";

    private EventId eventId;
    private VolunteerId volunteerId;
    private Hour hour;
    private Remark remark;

    public RecordBuilder() {
        eventId = new EventId(DEFAULT_EVENTID);
        volunteerId = new VolunteerId(DEFAULT_VOLUNTEERID);
        hour = new Hour(DEFAULT_HOUR);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordoCopy}.
     */
    public RecordBuilder(Record recordToCopy) {
        eventId = recordToCopy.getEventId();
        volunteerId = recordToCopy.getVolunteerId();
        hour = recordToCopy.getHour();
        remark = recordToCopy.getRemark();
    }

    /**
     * Sets the {@code EventId} of the {@code Record} that we are building.
     */
    public RecordBuilder withEventId(String eventId) {
        this.eventId = new EventId(eventId);
        return this;
    }

    /**
     * Sets the {@code VolunteerId} of the {@code Record} that we are building.
     */
    public RecordBuilder withVolunteerId(String volunteerId) {
        this.volunteerId = new VolunteerId(volunteerId);
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code Record} that we are building.
     */
    public RecordBuilder withHour(String hour) {
        this.hour = new Hour(hour);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Record} that we are building.
     */
    public RecordBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Record build() {
        return new Record(eventId, volunteerId, hour, remark);
    }

}
