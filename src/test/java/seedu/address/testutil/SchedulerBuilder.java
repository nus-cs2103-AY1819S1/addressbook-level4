package seedu.address.testutil;

import seedu.address.model.Scheduler;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building Scheduler objects.
 * Example usage: <br>
 * {@code Scheduler scheduler = new SchedulerBuilder().withEvent(CS2103_TUTORIAL).build();}
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
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public SchedulerBuilder withEvent(Event event) {
        scheduler.addEvent(event);
        return this;
    }

    public Scheduler build() {
        return scheduler;
    }
}
