package seedu.scheduler.commons.events.model;

import seedu.scheduler.commons.events.BaseEvent;
import seedu.scheduler.model.ReadOnlyScheduler;

/** Indicates the Scheduler in the model has changed*/
public class SchedulerChangedEvent extends BaseEvent {

    public final ReadOnlyScheduler data;

    public SchedulerChangedEvent(ReadOnlyScheduler data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
