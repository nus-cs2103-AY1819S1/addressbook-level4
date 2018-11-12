package seedu.scheduler.testutil;

import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.Event;

/**
 * A utility class to help with building Scheduler objects.
 * Example usage: <br>
 * {@code Scheduler scheduler = new SchedulerBuilder().withEvent(JANUARY_1_2018_SINGLE).build();}
 */
public class SchedulerBuilder {

    private Scheduler scheduler;

    public SchedulerBuilder() {
        scheduler = new Scheduler();
    }

    public SchedulerBuilder(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Adds a new {@code Event} to the {@code Scheduler} that we are building.
     */
    public SchedulerBuilder withEvent(Event event) {
        scheduler.addEvent(event);
        return this;
    }

    public Scheduler build() {
        return scheduler;
    }
}
