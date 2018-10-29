package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.NotificationHandler;
import seedu.address.storage.storageutil.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class XmlAdaptedNotificationHandler {

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime lastTipSentOn;
    @XmlElement
    private boolean isTipEnabled;
    @XmlElement
    private boolean isWarningEnabled;

    /**
     * Constructs an XmlAdaptedNotificationHandler.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNotificationHandler() {

    }

    /**
     * Converts a given NotificationHandler into this class for JAXB use.
     *
     * @param source source budget
     */
    public XmlAdaptedNotificationHandler(NotificationHandler source) {
        lastTipSentOn = source.getLastTipSentOn();
        isTipEnabled = source.isTipEnabled();
        isWarningEnabled = source.isWarningEnabled();
    }

    /**
     * Converts this jaxb-friendly adapted expense object into the model's NotificationHandler object.
     */
    public NotificationHandler toModelType() {
        return new NotificationHandler(lastTipSentOn, isTipEnabled, isWarningEnabled);
    }

}
