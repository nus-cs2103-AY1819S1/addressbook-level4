package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.notification.Notification;
import seedu.address.model.notification.Notification.NotificationType;
import seedu.address.model.notification.TipNotification;
import seedu.address.model.notification.WarningNotification;

//@author Snookerballs

/**
 * JAXB-friendly adapted version of Notification.
 */
public class XmlAdaptedNotification {

    @XmlElement
    private String header;
    @XmlElement
    private String body;
    @XmlElement
    private NotificationType type;

    /**
     * Constructs an XmlAdaptedNotification.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNotification() {
    }

    /**
     * Converts a given Notification into this class for JAXB use.
     *
     * @param source source budget
     */
    public XmlAdaptedNotification(Notification source) {
        this.header = source.getHeader();
        this.body = source.getBody();
        this.type = source.getNotificationType();
    }

    /**
     * Converts this jaxb-friendly adapted expense object into the model's Notification object.
     */
    public Notification toModelType() {
        if (type.equals(NotificationType.TIP)) {
            return new TipNotification(header, body);
        }
        return new WarningNotification(header, body);
    }
}
