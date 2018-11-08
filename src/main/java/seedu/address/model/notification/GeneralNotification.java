package seedu.address.model.notification;

/**
 * Notification class to display general notifications
 */
public class GeneralNotification extends Notification {
    public GeneralNotification (String header, String body) {
        super(header, body);
        type = NotificationType.WARNING;
    }


    public GeneralNotification(GeneralNotification notification) {
        super(notification);
    }
}
