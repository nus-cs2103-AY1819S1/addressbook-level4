package seedu.restaurant.commons.core.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.model.account.Account;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class UserSessionTest {

    @Before
    public void setUp() {
        UserSession.create(new AccountBuilder().build());
    }

    @AfterClass
    public static void tearDown() {
        // Make sure to unset the session, in case the last test case does not logs out
        UserSession.destroy();
    }

    @Test
    public void session_isAuthenticated_returnTrue() {
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }

    @Test
    public void session_update_returnTrue() {
        Account accountWithNewPassword = new AccountBuilder().withUsername("hellothere").build();
        UserSession.update(accountWithNewPassword);
        assertEquals(UserSession.getAccount().getUsername().toString(), "hellothere");
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }

    @Test
    public void session_isNotAuthenticated_returnTrue() {
        UserSession.destroy();
        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getAccount());
    }
}
