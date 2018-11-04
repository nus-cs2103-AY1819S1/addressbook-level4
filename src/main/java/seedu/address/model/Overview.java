package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains methods used when calling the overview command.
 */
public class Overview {
    private ObservableList<Volunteer> volunteers;
    private ObservableList<Event> events;
    private ObservableList<Record> records;

    private int numOfOngoingEvents;
    private int numOfUpcomingEvents;
    private int numOfCompletedEvents;

    public Overview(ObservableList<Volunteer> volunteers,
                    ObservableList<Event> events,
                    ObservableList<Record> records) {
        this.volunteers = volunteers;
        this.events = events;
        this.records = records;

        calculateNumOfEvents();
    }

    /**
     * This method helps to get the number of events for the respective types based on time.
     */
    private void calculateNumOfEvents() {
        numOfUpcomingEvents = 0;
        numOfOngoingEvents = 0;
        numOfCompletedEvents = 0;


        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            int status = DateTimeUtil.getEventStatus(e.getStartDate(), e.getStartTime(),
                    e.getEndDate(), e.getEndTime());
            if (status == DateTimeUtil.UPCOMING_EVENT) {
                numOfUpcomingEvents++;
            }
            if (status == DateTimeUtil.ONGOING_EVENT) {
                numOfOngoingEvents++;
            }
            if (status == DateTimeUtil.COMPLETED_EVENT) {
                numOfCompletedEvents++;
            }
        }
    }

    public int getNumOfOngoingEvents() {
        return numOfOngoingEvents;
    }

    public int getNumOfUpcomingEvents() {
        return numOfUpcomingEvents;
    }

    public int getNumOfCompletedEvents() {
        return numOfCompletedEvents;
    }
}
