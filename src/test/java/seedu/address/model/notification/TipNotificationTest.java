package seedu.address.model.notification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@Snookerballs
public class TipNotificationTest extends NotificationTest {
    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TipNotification((Tips) null));
        Assert.assertThrows(NullPointerException.class, () -> new TipNotification((TipNotification) null));
    }

    @Test
    public void constructor_nullStrings_throwsIllegalArgumentException() {

        Assert.assertThrows(NullPointerException.class, () -> new TipNotification(null, null));
    }

    @Test
    public void getNotificationType() {
        assertEquals(new TipNotification(VALID_HEADER, VALID_BODY).type, Notification.NotificationType.TIP);
    }

    @Test
    public void isSameNotification() {
        assertTrue(new TipNotification(VALID_HEADER, VALID_BODY)
                .isSameNotification(new TipNotification("aa", "bb")));
        assertFalse(new TipNotification(VALID_HEADER, VALID_BODY)
                .isSameNotification(new TipNotification("bb", "bb")));
    }


}
