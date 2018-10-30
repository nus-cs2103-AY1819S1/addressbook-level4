package seedu.address.model.notification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalNotifications.ALMOST_OVERBUDGET_WARNING;
import static seedu.address.testutil.TypicalNotifications.DATE_FORMAT;
import static seedu.address.testutil.TypicalNotifications.OVERBUDGET_WARNING;
import static seedu.address.testutil.TypicalNotifications.TIP;
import static seedu.address.testutil.TypicalNotifications.asNotificationHandler;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;


//@@author Snookerballs
public class NotificationHandlerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NotificationHandler handler = new NotificationHandler(LocalDateTime.parse(DATE_FORMAT),
            true , true);
    @BeforeEach
    public void initializeList() {
        handler = new NotificationHandler();
    }

    @Test
    public void add_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        handler.add(null);
    }

    @Test
    public void equals() {
        //Differing objects in list
        handler.add(OVERBUDGET_WARNING);
        handler.add(ALMOST_OVERBUDGET_WARNING);
        assertNotEquals(handler, asNotificationHandler());

        //Valid
        handler.add(TIP);
        assertEquals(handler, asNotificationHandler());

        //Differing  Date
        handler = new NotificationHandler();
        assertNotEquals(handler, asNotificationHandler());

        //Differing isWarningEnabled
        handler = new NotificationHandler(LocalDateTime.parse(DATE_FORMAT),
                true , false);
        assertNotEquals(handler, asNotificationHandler());

        //Differing isTipEnabled
        handler = new NotificationHandler(LocalDateTime.parse(DATE_FORMAT),
                false , true);
        assertNotEquals(handler, asNotificationHandler());
    }
}
