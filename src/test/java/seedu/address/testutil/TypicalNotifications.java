package seedu.address.testutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.NotificationHandler;
import seedu.address.model.notification.TipNotification;
import seedu.address.model.notification.WarningNotification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

//@@author Snookerballs
public class TypicalNotifications {
    private TypicalNotifications(){}

    public static final WarningNotification OVERBUDGET_WARNING =
            new WarningNotification("WARNING", "You are Overbudget!");
    public static final WarningNotification ALMOST_OVERBUDGET_WARNING =
            new WarningNotification("WARNING", "You are about to be overbudget!");
    public static final TipNotification TIP =
            new TipNotification("Save Money", "Save money by saving some money");

    public static final String DATE_FORMAT = "2018-10-31T01:42:04.021756";

    public static ArrayList<Notification> getTypicalNotifications() {
        return new ArrayList<>(Arrays.asList(OVERBUDGET_WARNING, ALMOST_OVERBUDGET_WARNING, TIP));
    }

    public static NotificationHandler asNotificationHandler() {
        NotificationHandler handler = new NotificationHandler(LocalDateTime.parse(DATE_FORMAT),
                true, true);
        handler.add(OVERBUDGET_WARNING);
        handler.add(ALMOST_OVERBUDGET_WARNING);
        handler.add(TIP);
        return handler;
    }
}

