package seedu.scheduler.logic;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Event;

/**
 * Generates a list of repeated event for the scheduler if events are repeated events.
 */
public class RepeatEventGenerator {

    private static RepeatEventGenerator instance = null;

    private RepeatEventGenerator() {
    }

    public static RepeatEventGenerator getInstance() {
        if (instance == null) {
            instance = new RepeatEventGenerator();
        }
        return instance;
    }

    /**
     * Generate all repeated events from {@code targetEvent} according to its repeat type.
     * {@code targetEvent} must have a valid event repeat type.
     * Returns an unmodifiable list of repeated events.
     */
    public List<Event> generateAllRepeatedEvents(Event targetEvent) {
        switch(targetEvent.getRepeatType()) {
        case DAILY:
            return Collections.unmodifiableList(generateDailyRepeatEvents(targetEvent));
        case WEEKLY:
            return Collections.unmodifiableList(generateWeeklyRepeatEvents(targetEvent));
        case MONTHLY:
            return Collections.unmodifiableList(generateMonthlyRepeatEvents(targetEvent));
        case YEARLY:
            return Collections.unmodifiableList(generateYearlyRepeatEvents(targetEvent));
        default:
            return List.of(targetEvent);
        }
    }

    /**
     * Generate all events that are repeated daily from {@code targetEvent}.
     * Returns a list of events that are repeated daily.
     */
    public List<Event> generateDailyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)
                && !repeatStartDateTime.plus(durationDiff).isAfter(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getEventSetUid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime(),
                    targetEvent.getTags(),
                    targetEvent.getReminderDurationList()
            ));
            repeatStartDateTime = repeatStartDateTime.plusDays(1);
        }
        return repeatedEventList;
    }

    /**
     * Generate all events that are repeated weekly from {@code targetEvent}.
     * Returns a list of events that are repeated weekly.
     */
    public List<Event> generateWeeklyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)
                && !repeatStartDateTime.plus(durationDiff).isAfter(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getEventSetUid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime(),
                    targetEvent.getTags(),
                    targetEvent.getReminderDurationList()
            ));
            repeatStartDateTime = repeatStartDateTime.plusWeeks(1);
        }
        return repeatedEventList;
    }

    /**
     * Generate all events that are repeated monthly from {@code targetEvent}.
     * Returns a list of events that are repeated monthly.
     */
    public List<Event> generateMonthlyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)
                && !repeatStartDateTime.plus(durationDiff).isAfter(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getEventSetUid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime(),
                    targetEvent.getTags(),
                    targetEvent.getReminderDurationList()
            ));
            repeatStartDateTime = repeatStartDateTime.with((temporal) -> {
                do {
                    try {
                        temporal = temporal.plus(1, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH,
                                targetEvent.getStartDateTime().value.getDayOfMonth());
                    } catch (DateTimeException e) {
                        temporal = temporal.plus(1, ChronoUnit.MONTHS);
                    }
                } while (temporal.get(ChronoField.DAY_OF_MONTH)
                        != targetEvent.getStartDateTime().value.getDayOfMonth());
                return temporal;
            });
        }
        return repeatedEventList;
    }

    /**
     * Generate all events that are repeated yearly from {@code targetEvent}.
     * Returns a list of events that are repeated yearly.
     */
    public List<Event> generateYearlyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)
                && !repeatStartDateTime.plus(durationDiff).isAfter(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getEventSetUid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime(),
                    targetEvent.getTags(),
                    targetEvent.getReminderDurationList()
            ));
            repeatStartDateTime = repeatStartDateTime.with((temporal) -> {
                do {
                    try {
                        temporal = temporal.plus(1, ChronoUnit.YEARS).with(ChronoField.DAY_OF_MONTH,
                                targetEvent.getStartDateTime().value.getDayOfMonth());
                    } catch (DateTimeException e) {
                        temporal = temporal.plus(1, ChronoUnit.YEARS);
                    }
                } while (temporal.get(ChronoField.DAY_OF_MONTH)
                        != targetEvent.getStartDateTime().value.getDayOfMonth());
                return temporal;
            });
        }
        return repeatedEventList;
    }

}
