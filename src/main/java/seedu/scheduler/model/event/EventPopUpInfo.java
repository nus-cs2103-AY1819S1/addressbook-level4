package seedu.scheduler.model.event;

import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventPopUpInfo implements Comparable<EventPopUpInfo> {
    private UUID uid;
    private UUID uuid;
    private EventName eventName;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Description description;
    private Venue venue;
    private Duration duration;
    private DateTime popUpDateTime;

    public EventPopUpInfo(UUID uid, UUID uuid, EventName eventName, DateTime startDateTime, DateTime endDateTime,
                          Description description, Venue venue, Duration duration) {
        requireAllNonNull(uid, uuid, eventName, startDateTime, endDateTime, description,
                venue, duration);
        this.uid = uid;
        this.uuid = uuid;
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

    public UUID getUid() {
        return uid;
    }


    public UUID getUuid() {
        return uuid;
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

    public int compareTo(EventPopUpInfo other) {
        return this.popUpDateTime.compareTo(other.popUpDateTime);
    }

    public DateTime getPopUpDateTime () {
        return popUpDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return ((this == other) || (
                other instanceof EventPopUpInfo
                        && this.uid.equals(((EventPopUpInfo) other).getUid())
                        && this.duration.equals(((EventPopUpInfo) other).getDuration())));
    }
}

