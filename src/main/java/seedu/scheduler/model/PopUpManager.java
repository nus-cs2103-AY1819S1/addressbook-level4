package seedu.scheduler.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.EventPopUpInfo;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.ui.PopUp;

/**
 * Extract PopUp info and run in the background
 */
public class PopUpManager {

    private static final Logger logger = LogsCenter.getLogger(PopUpManager.class);
    private static PopUpManager instance;
    private PriorityQueue<EventPopUpInfo> popUpQueue;
    // private static ArrayList<EventPopUpInfo> pastPopUps;

    private PopUpManager() {
        popUpQueue = new PriorityQueue<>();
        //pastPopUps = new ArrayList<>();
    }

    public static PopUpManager getInstance() {
        if (instance == null) {
            instance = new PopUpManager();
        }
        return instance;
    }

    /**
     * Get an ArrayList of the popUpQueue
     * @return ArrayList
     */
    public ArrayList<EventPopUpInfo> getArray() {
        return new ArrayList(popUpQueue);
    }

    /**
     * Initialise the popUpQueue when the app is open
     * @param readOnlyScheduler
     */
    public void syncPopUpInfo(ReadOnlyScheduler readOnlyScheduler) {
        ObservableList<Event> eventList = readOnlyScheduler.getEventList();
        popUpQueue.addAll(generatePopUpInfoListFromEvents(eventList));
    }

    /**
     * Add Single Event
     * EventPopUpInfo to the popUpQueue given an Event
     * @param event
     */
    public void add(Event event) {
        popUpQueue.addAll(generatePopUpInfoListFromEvent(event));
    }

    /**
     * Add Recurring Event's EventPopUpInfo
     * Add single EventPopUpInfo to the popUpQueue given a list of Event
     * @param events
     */
    public void add(Collection<Event> events) {
        popUpQueue.addAll(generatePopUpInfoListFromEvents(events));
    }

    /**
     * Edit Single Event's EventPopUpInfo
     * Every time an Event is updated, add all future EventPopUpInfo to the queue and ignore the passed.
     * @param target
     * @param editedEvent
     */
    public void edit(Event target, Event editedEvent) {
        if (!target.getReminderDurationList().equals(editedEvent.getReminderDurationList())) {
            delete(target);
            add(editedEvent);
        }
    }

    /**
     * Edit All Recurring Event's EventPopUpInfo
     * Every time an Event is updated, add all future EventPopUpInfo to the queue and ignore the passed.
     * @param target
     * @param editedEvents
     */
    public void editAll(Event target, List<Event> editedEvents) {
        Event firstEvent = editedEvents.get(0);
        if (!target.getReminderDurationList().equals(firstEvent.getReminderDurationList())) {
            deleteAll(target);
            add(editedEvents);
        }
    }

    /**
     * Edit Upcoming Recurring Event's EventPopUpInfo
     * Every time an Event is updated, add all future EventPopUpInfo to the queue and ignore the passed.
     * @param target
     * @param editedEvents
     */
    public void editUpcoming(Event target, List<Event> editedEvents) {
        Event firstEvent = editedEvents.get(0);
        if (!target.getReminderDurationList().equals(firstEvent.getReminderDurationList())) {
            deleteUpcoming(target);
            add(editedEvents);
        }
    }


    /**
     * Delete Single Event's EventPopUpInfo
     * Delete all eventPopUpInfo in the popUpQueue that shares the same Uid as the deleted event
     * @param target
     */
    public void delete(Event target) {
        PriorityQueue<EventPopUpInfo> newQueue = new PriorityQueue<>();
        for (EventPopUpInfo eventPopUpInfo : popUpQueue) {
            if (!eventPopUpInfo.getUid().equals(target.getUid())) {
                newQueue.add(eventPopUpInfo);
            }
        }
        popUpQueue = newQueue;
    }

    /**
     * Delete All Recurring Event's EventPopUpInfo
     * Delete all eventPopUpInfo in the popUpQueue that shares the same Uid as the deleted event
     * @param target
     */
    public void deleteAll(Event target) {
        PriorityQueue<EventPopUpInfo> newQueue = new PriorityQueue<>();
        for (EventPopUpInfo eventPopUpInfo : popUpQueue) {
            if (!isRecurringEvent(eventPopUpInfo, target)) {
                newQueue.add(eventPopUpInfo);
            }
        }
        popUpQueue = newQueue;
    }

    /**
     * Delete upcoming EventPopUpInfo
     * Delete all eventPopUpInfo in the popUpQueue that shares the same Uid as the deleted event
     * @param target
     */
    public void deleteUpcoming(Event target) {
        PriorityQueue<EventPopUpInfo> newQueue = new PriorityQueue<>();
        for (EventPopUpInfo eventPopUpInfo : popUpQueue) {
            if (!isUpcomingEvent(eventPopUpInfo, target)) {
                newQueue.add(eventPopUpInfo);
            }
        }
        popUpQueue = newQueue;
    }

    /**
     * Check if eventPopUpInfo belongs to an upcoming event compared to target
     * @param event
     * @param target
     * @return
     */
    private Boolean isUpcomingEvent(EventPopUpInfo event, Event target) {
        return (event.getUuid().equals(target.getUuid())
                        && event.getStartDateTime().compareTo(target.getStartDateTime()) > 0);
    }

    /**
     * Check if eventPopUpInfo belongs to an event that is a recurring event of target
     * @param event
     * @param target
     * @return
     */
    private Boolean isRecurringEvent(EventPopUpInfo event, Event target) {
        return event.getUuid().equals(target.getUuid());
    }

    /**
     * Generate a list of EventPopUpInfo objects of different Durations given an Event
     * @param event
     * @return
     */
    private ArrayList<EventPopUpInfo> generatePopUpInfoListFromEvent(Event event) {
        ArrayList<EventPopUpInfo> result = new ArrayList<>();
        UUID uid = event.getUid();
        UUID uuid = event.getUuid();
        EventName eventName = event.getEventName();
        DateTime startDateTime = event.getStartDateTime();
        DateTime endDateTime = event.getEndDateTime();
        Description description = event.getDescription();
        Venue venue = event.getVenue();
        ReminderDurationList reminderDurationList = event.getReminderDurationList();

        for (Duration duration : reminderDurationList.get()) {
            result.add(new EventPopUpInfo(uid, uuid, eventName, startDateTime, endDateTime, description, venue,
                    duration));
        }
        return result;
    }

    /**
     * Generate a list of EventPopUpInfo objects of different Durations given a list of Events
     * @param events
     * @return
     */
    private ArrayList<EventPopUpInfo> generatePopUpInfoListFromEvents(Collection<Event> events) {
        ArrayList<EventPopUpInfo> result = new ArrayList<>();
        for (Event event : events) {
            result.addAll(generatePopUpInfoListFromEvent(event));
        }
        return result;
    }

    /**
     * Main
     * checking for PopUp in the background
     */
    public void startRunning() {
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                while (true) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTime currentDateTime = new DateTime(now);
                    // logger.info("Checking current event popUp info queue...");

                    // check the event queue date time
                    if (!popUpQueue.isEmpty()) {
                        DateTime frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                        //logger.info(frontEventDateTime.toString());
                        while (frontEventDateTime.isPast(currentDateTime)) {
                            EventPopUpInfo currentPopUp = popUpQueue.peek();
                            displayPopUp("Past Reminder!", currentPopUp.getDescription().toString());
                            // pastPopUps.add(currentPopUp);
                            popUpQueue.remove();
                            if (!popUpQueue.isEmpty()) {
                                frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                            } else {
                                break;
                            }
                        }

                    }

                    if (!popUpQueue.isEmpty()) {
                        //logger.info("Checking for incoming events");
                        DateTime frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                        //logger.info(frontEventDateTime.toString());
                        while (frontEventDateTime.isClose(currentDateTime)) {
                            EventPopUpInfo currentPopUp = popUpQueue.peek();
                            displayPopUp("Time's Up!", currentPopUp.getDescription().toString());
                            // pastPopUps.add(currentPopUp);
                            popUpQueue.remove();
                            if (!popUpQueue.isEmpty()) {
                                frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                            } else {
                                break;
                            }
                        }
                    }

                    // Sleep for 5 seconds after each loop of checking, to give the app some buffer time
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return null;
            }
        };

        Thread backgroundThread = new Thread(task);
        backgroundThread.start();
    }

    /**
     * Display PopUp message
     * @param title
     * @param description
     */
    private void displayPopUp (String title, String description) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PopUp popUp = new PopUp();
                popUp.display(title, description);
            }
        });
    }
}
