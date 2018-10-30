package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.storage.XmlAdaptedNotification.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.notification.Notification.NotificationType;
import seedu.address.model.notification.NotificationTest;
import seedu.address.testutil.Assert;

//@@author Snookerballs

public class XmlAdaptedNotificationTest {
    private static final String MISMATCHED_HEADER = "Header";
    private static final String MISMATCHED_BODY = "Header";
    private static final NotificationType MISMATCHED_TYPE = NotificationType.WARNING;

    @Test
    public void testEquals() {
        XmlAdaptedNotification validXmlAdaptedNotification = new XmlAdaptedNotification(NotificationTest.VALID_HEADER,
                NotificationTest.VALID_BODY,
                NotificationTest.VALID_TYPE);
        assertNotEquals(validXmlAdaptedNotification, new XmlAdaptedNotification(MISMATCHED_HEADER,
                MISMATCHED_BODY, MISMATCHED_TYPE));
        assertNotEquals(validXmlAdaptedNotification, new XmlAdaptedNotification(NotificationTest.VALID_HEADER,
                MISMATCHED_BODY, MISMATCHED_TYPE));
        assertNotEquals(validXmlAdaptedNotification, new XmlAdaptedNotification(NotificationTest.VALID_HEADER,
                NotificationTest.VALID_BODY, MISMATCHED_TYPE));
        assertEquals(validXmlAdaptedNotification, new XmlAdaptedNotification(NotificationTest.VALID_HEADER,
                NotificationTest.VALID_BODY, NotificationTest.VALID_TYPE));
    }

    @Test
    public void testToModelType_nullHeader_throwsIllegalValueException() {
        XmlAdaptedNotification invalidXmlAdaptedNotification = new XmlAdaptedNotification(null,
                NotificationTest.VALID_BODY,
                NotificationTest.VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "header");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, invalidXmlAdaptedNotification::toModelType);
    }

    @Test
    public void testToModelType_nullBody_throwsIllegalValueException() {
        XmlAdaptedNotification invalidXmlAdaptedNotification = new XmlAdaptedNotification(NotificationTest.VALID_HEADER,
                null, NotificationTest.VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "body");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, invalidXmlAdaptedNotification::toModelType);
    }

    @Test
    public void testToModelType_nullType_throwsIllegalValueException() {
        XmlAdaptedNotification invalidXmlAdaptedNotification = new XmlAdaptedNotification(NotificationTest.VALID_HEADER,
                NotificationTest.VALID_BODY,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "type");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, invalidXmlAdaptedNotification::toModelType);
    }

}
