package seedu.address.model.record;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.event.EventId;
import seedu.address.model.volunteer.VolunteerId;

/**
 * Represents a volunteer's event record in application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {
    private final EventId eventId;
    private final VolunteerId volunteerId;
    private final Hour hour;
    private final Remark remark;

    private int localIndex;
    private String volunteerName;
    private String phoneNo;

    /**
     * Every field must be present and not null.
     */
    public Record(EventId eventId, VolunteerId volunteerId, Hour hour, Remark remark) {
        requireAllNonNull(eventId, volunteerId, hour, remark);
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.hour = hour;
        this.remark = remark;
    }

    public Record(Hour hour, Remark remark) {
        requireAllNonNull(hour, remark);
        this.eventId = null;
        this.volunteerId = null;
        this.hour = hour;
        this.remark = remark;
    }

    public EventId getEventId() {
        return eventId;
    }

    public VolunteerId getVolunteerId() {
        return volunteerId;
    }

    public Hour getHour() {
        return hour;
    }

    public Remark getRemark() {
        return remark;
    }

    public int getLocalIndex() {
        return localIndex;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setLocalIndex(int index) {
        this.localIndex = index;
    }

    public void setVolunteerName(String name) {
        this.volunteerName = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Returns true if both records have the same eventId and volunteerId
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.getEventId().equals(getEventId())
                && otherRecord.getVolunteerId().equals(getVolunteerId());
    }

    /**
     * Returns true if both records have the same identity and data fields.
     * This defines a stronger notion of equality between two records.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return otherRecord.getEventId().equals(getEventId())
                && otherRecord.getVolunteerId().equals(getVolunteerId())
                && otherRecord.getHour().equals(getHour())
                && otherRecord.getRemark().equals(getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventId, volunteerId, hour, remark);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getVolunteerName())
                .append(" Hour: ")
                .append(getHour())
                .append(" Remark: ")
                .append(getRemark());
        return builder.toString();
    }


}
