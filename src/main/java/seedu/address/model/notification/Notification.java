package seedu.address.model.notification;

/**
 * Represents a notification in the expense tracker
 */
//@@Snookerballs
public abstract class Notification {
    public static final String MESSAGE_COST_CONSTRAINTS =
            "Notification header and body should not be blank";

    protected String header;

    protected String body;

    protected NotificationType type;

    /**
     * The different types of notifications
     */
    public enum NotificationType {
        WARNING, TIP
    }

    public Notification() {

    }

    public Notification(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public boolean isSameNotification(Notification otherNotification) {
        return (otherNotification.header == header) && (otherNotification.body == body);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getHeader())
                .append(getBody());
        return builder.toString();
    }

    public NotificationType getNotificationType() {
        return type;
    }

}
