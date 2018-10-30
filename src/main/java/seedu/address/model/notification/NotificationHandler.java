package seedu.address.model.notification;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;


/**
 * Handles the generation and storage of notifications.
 */
//@@Snookerballs
public class NotificationHandler implements Iterable<Notification> {
    private static final int DAYS_BEFORE_SENDING_TIP = 1;
    private static final double WARNING_NOTIFICATION_TRESHOLD = 0.8;
    private static final int MAXIMUM_NUMBER_OF_NOTIFICATIONS = 10;
    private static final LocalDateTime DEFAULT_LOCAL_DATE_TIME = LocalDateTime.parse("2018-10-31T01:42:04.02175");

    private LocalDateTime lastTipSentOn;
    private boolean isTipEnabled;
    private boolean isWarningEnabled;
    private ObservableList<Notification> internalList = FXCollections.observableArrayList();

    public NotificationHandler() {
        lastTipSentOn = DEFAULT_LOCAL_DATE_TIME;
        isTipEnabled = true;
        isWarningEnabled = true;
    }

    public NotificationHandler(LocalDateTime date, boolean isTipEnabled, boolean isWarningEnabled) {
        this.lastTipSentOn = date;
        this.isTipEnabled = isTipEnabled;
        this.isWarningEnabled = isWarningEnabled;
    }

    public NotificationHandler(List<Notification> internalList) {
       super();
       this.internalList = FXCollections.observableArrayList(internalList);
    }

    public void setNotifications(ObservableList<Notification> notifications) {
        ArrayList<Notification> list = new ArrayList<>();
        notifications.forEach(n -> list.add(n));
        this.internalList.setAll(list);
    }


    /**
     * Checks if a tip notification should be sent for that day
     * @return true if a tip should be sent,false if otherwise.
     */
    public boolean isTimeToSendTip() {
        boolean isTimeToSend = false;
        if (LocalDateTime.now().isAfter(lastTipSentOn)) {
            lastTipSentOn = LocalDateTime.now().plusDays(DAYS_BEFORE_SENDING_TIP);
            isTimeToSend = true;
        }
        return isTimeToSend && isTipEnabled;
    }

    /**
     * Checks if a warning notification should be sent
     *
     * @return true if a warning should be sent,false if otherwise.
     */
    public boolean isTimeToSendWarning(Budget budget) {
        return isWarningEnabled && budget.getBudgetPercentage() > WARNING_NOTIFICATION_TRESHOLD;
    }

    /**
     * Adds a notification to the internalList.
     *
     * @param notification to add
     */
    public void add(Notification notification) {
        requireNonNull(notification);
        internalList.add(0, notification);
        if (internalList.size() > MAXIMUM_NUMBER_OF_NOTIFICATIONS) {
            internalList.remove(internalList.size() - 1);
        }
    }

    public int size() {
        return internalList.size();
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public Iterator<Notification> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Notification> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public void setTipEnabled(boolean tipEnabled) {
        isTipEnabled = tipEnabled;
    }

    public void setWarningEnabled(boolean warningEnabled) {
        isWarningEnabled = warningEnabled;
    }

    public LocalDateTime getLastTipSentOn() {
        return lastTipSentOn;
    }

    public boolean isTipEnabled() {
        return isTipEnabled;
    }

    public boolean isWarningEnabled() {
        return isWarningEnabled;
    }

    public ObservableList<Notification> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof NotificationHandler)) {
            return false;
        }

        NotificationHandler handler = (NotificationHandler) obj;

        return this.isWarningEnabled == handler.isWarningEnabled
                && this.isTipEnabled == handler.isTipEnabled
                && this.lastTipSentOn.getDayOfMonth() == handler.lastTipSentOn.getDayOfMonth()
                && this.lastTipSentOn.getMonth().equals(handler.lastTipSentOn.getMonth())
                &&this.lastTipSentOn.getYear() == handler.lastTipSentOn.getYear()
                && this.internalList.equals(handler.internalList);
    }
}
