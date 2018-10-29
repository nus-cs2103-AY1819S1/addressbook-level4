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
     * Add EventPopUpInfo to the popUpQueue given an Event
     * @param event
     */
    public void add(Event event) {
        popUpQueue.addAll(generatePopUpInfoListFromEvent(event));
    }

    /**
     * Add EventPopUpInfo to the popUpQueue given a list of Event
     * @param events
     */
    public void add(Collection<Event> events) {
        popUpQueue.addAll(generatePopUpInfoListFromEvents(events));
    }

    /**
     * Every time an Event is updated, add all future EventPopUpInfo to the queue and ignore the passed
     * @param event
     * @param editedEvent
     */
    public void edit(Event event, Event editedEvent) {
        if (!event.getReminderDurationList().equals(editedEvent.getReminderDurationList())) {
            delete(event);
            add(editedEvent);
        }
    }

    /**
     * Delete all eventPopUpInfo in the popUpQueue that shares the same Uid as the deleted event
     * @param event
     */
    public void delete(Event event) {
        PriorityQueue<EventPopUpInfo> newQueue = new PriorityQueue<>();
        for (EventPopUpInfo eventPopUpInfo : popUpQueue) {
            if (!eventPopUpInfo.getUid().equals(event.getUid())) {
                newQueue.add(eventPopUpInfo);
            }
        }
        popUpQueue = newQueue;
    }


    /**
     * Generate a list of EventPopUpInfo objects of different Durations given an Event
     * @param event
     * @return
     */
    private ArrayList<EventPopUpInfo> generatePopUpInfoListFromEvent(Event event) {
        ArrayList<EventPopUpInfo> result = new ArrayList<>();
        UUID uid = event.getUid();
        EventName eventName = event.getEventName();
        DateTime startDateTime = event.getStartDateTime();
        DateTime endDateTime = event.getEndDateTime();
        Description description = event.getDescription();
        Venue venue = event.getVenue();
        ReminderDurationList reminderDurationList = event.getReminderDurationList();

        for (Duration duration : reminderDurationList.get()) {
            result.add(new EventPopUpInfo(uid, eventName, startDateTime, endDateTime, description, venue, duration));
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
     * checking for PopUp in the background
     */
    public void startRunning() {
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                while (true) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTime currentDateTime = new DateTime(now);
                    logger.info("Checking current event popUp info queue...");

                    // check the event queue date time
                    if (!popUpQueue.isEmpty()) {
                        DateTime frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                        logger.info(frontEventDateTime.toString());
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
                        logger.info("Checking for incoming events");
                        DateTime frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                        logger.info(frontEventDateTime.toString());
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
