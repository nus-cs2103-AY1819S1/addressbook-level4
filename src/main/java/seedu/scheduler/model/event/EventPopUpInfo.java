package seedu.scheduler.model.event;

import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Create EventPopUpInfo that store the info to insert into the popUpQueue
 */
public class EventPopUpInfo implements Comparable<EventPopUpInfo> {
    private UUID eventUid;
    private UUID eventSetUid;
    private EventName eventName;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Description description;
    private Venue venue;
    private Duration duration;
    private DateTime popUpDateTime;

    public EventPopUpInfo(UUID eventUid, UUID eventSetUid,
                          EventName eventName, DateTime startDateTime, DateTime endDateTime,
                          Description description, Venue venue, Duration duration) {
        requireAllNonNull(eventUid, eventSetUid,
                eventName, startDateTime, endDateTime,
                description, venue, duration);
        this.eventUid = eventUid;
        this.eventSetUid = eventSetUid;
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.venue = venue;
        this.duration = duration;
        this.popUpDateTime = calculatePopUpTime(this.duration, this.startDateTime);
    }

    /**
     * calculate PopUpTime = startTime - duration
     * @param duration
     * @param startDateTime
     */
    public static DateTime calculatePopUpTime(Duration duration, DateTime startDateTime) {
        Long secondsToMinus = duration.getSeconds() * -1;
        LocalDateTime popUpTime = startDateTime.getLocalDateTime().plusSeconds(secondsToMinus);
        return new DateTime(popUpTime);
    }

    public UUID getEventUid() {
        return eventUid;
    }

    public UUID getEventSetUid() {
        return eventSetUid;
    }

    public EventName getEventName() {
        return eventName;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public Description getDescription() {
        return description;
    }

    public Venue getVenue() {
        return venue;
    }

    public Duration getDuration() {
        return duration;
    }

    public DateTime getPopUpDateTime () {
        return popUpDateTime;
    }

    /**
     * get Display for events
     * @return
     */
    public String getPopUpDisplay() {
        String result = "";
        result = result + "Venue: " + venue.toString() + "\n" + startDateTime.getPrettyString()
                + " - " + endDateTime.getPrettyString();
        return result;
    }

    public int compareTo(EventPopUpInfo other) {
        return this.popUpDateTime.compareTo(other.popUpDateTime);
    }



    @Override
    public boolean equals(Object other) {
        return ((this == other) || (
                other instanceof EventPopUpInfo
                        && this.eventUid.equals(((EventPopUpInfo) other).getEventUid())
                        && this.eventSetUid.equals(((EventPopUpInfo) other).getEventSetUid())
                        && this.eventName.equals(((EventPopUpInfo) other).getEventName())
                        && this.startDateTime.equals(((EventPopUpInfo) other).getStartDateTime())
                        && this.endDateTime.equals(((EventPopUpInfo) other).getEndDateTime())
                        && this.description.equals(((EventPopUpInfo) other).getDescription())
                        && this.venue.equals(((EventPopUpInfo) other).getVenue())
                        && this.duration.equals(((EventPopUpInfo) other).getDuration())
                        && this.popUpDateTime.equals(((EventPopUpInfo) other).getPopUpDateTime())));
    }
}

