package seedu.address.model;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventPopUpInfo;
import seedu.address.ui.PopUp;

public class PopUpManager {
    private PriorityQueue<EventPopUpInfo> popUpQueue;
    private static final Logger logger = LogsCenter.getLogger(PopUpManager.class);

    public PopUpManager(ReadOnlyScheduler readOnlyScheduler) {

        popUpQueue = new PriorityQueue<>();
        ObservableList<Event> eventList = readOnlyScheduler.getEventList();
        for (Event event: eventList) {
            popUpQueue.add(new EventPopUpInfo(
                    event.getDescription().toString(),
                    event.getStartDateTime()));
        }
    }

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
                            displayPopUp("Event already passed!", popUpQueue.peek().getDescription());
                            popUpQueue.remove();
                            if (!popUpQueue.isEmpty()) {
                                frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                            } else break;
                        }

                    }

                    if (!popUpQueue.isEmpty()) {
                        logger.info("Checking for incoming events");
                        DateTime frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                        logger.info(frontEventDateTime.toString());
                        while (frontEventDateTime.isClose(currentDateTime)) {
                            displayPopUp("Time's Up!", popUpQueue.peek().getDescription());
                            popUpQueue.remove();
                            if (!popUpQueue.isEmpty()) {
                                frontEventDateTime = popUpQueue.peek().getPopUpDateTime();
                            } else break;
                        }
                    }

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
