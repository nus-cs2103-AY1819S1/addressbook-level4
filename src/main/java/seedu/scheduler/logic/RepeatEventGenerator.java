package seedu.scheduler.logic;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
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
        assert targetEvent != null;

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
            switch (targetEvent.getRepeatType()) {
            case DAILY:
                repeatStartDateTime = repeatStartDateTime.plusDays(1);
                break;
            case WEEKLY:
                repeatStartDateTime = repeatStartDateTime.plusWeeks(1);
                break;
            case MONTHLY:
                repeatStartDateTime = repeatStartDateTime.with(
                        new SameDayOfMonthTemporalAdjuster(targetEvent, ChronoUnit.MONTHS));
                break;
            case YEARLY:
                repeatStartDateTime = repeatStartDateTime.with(
                        new SameDayOfMonthTemporalAdjuster(targetEvent, ChronoUnit.YEARS));
                break;
            default:
                return Collections.unmodifiableList(repeatedEventList);
            }
        }
        return Collections.unmodifiableList(repeatedEventList);
    }

    /**
     * Custom Temporal Adjuster that helps adjust date to the next date
     * according to its {@code unitType}.
     */
    private class SameDayOfMonthTemporalAdjuster implements TemporalAdjuster {
        private Event targetEvent;
        private ChronoUnit unitType;

        private SameDayOfMonthTemporalAdjuster(Event targetEvent, ChronoUnit unitType) {
            this.targetEvent = targetEvent;
            this.unitType = unitType;
        }

        @Override
        public Temporal adjustInto(Temporal temporal) {
            do {
                try {
                    temporal = temporal.plus(1, unitType).with(ChronoField.DAY_OF_MONTH,
                            targetEvent.getStartDateTime().value.getDayOfMonth());
                } catch (DateTimeException e) {
                    temporal = temporal.plus(1, unitType);
                }
            } while (temporal.get(ChronoField.DAY_OF_MONTH)
                    != targetEvent.getStartDateTime().value.getDayOfMonth());
            return temporal;
        }
    }

}
