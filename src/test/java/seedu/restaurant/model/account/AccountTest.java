package seedu.restaurant.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ONE;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_TWO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void check_toString() {
        Account adminAccount = new AccountBuilder(DEMO_ADMIN).build();

        // same username
        assertEquals(adminAccount.toString(), DEMO_ADMIN.getUsername().toString());

        // different username
        assertNotEquals(adminAccount.toString(), DEMO_ONE.getUsername().toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Account adminAccount = new AccountBuilder(DEMO_ADMIN).build();
        assertEquals(DEMO_ADMIN, adminAccount);

        // same object -> returns true
        assertEquals(DEMO_ADMIN, DEMO_ADMIN);

        // null -> returns false
        assertNotEquals(null, DEMO_ADMIN);

        // different type -> returns false
        assertNotEquals(5, DEMO_ADMIN);

        // different account -> returns false
        Account demoAccount = new AccountBuilder(DEMO_ONE).build();
        assertNotEquals(DEMO_ADMIN, demoAccount);

        // different username -> returns false
        Account editedAdminAccount = new AccountBuilder(DEMO_ADMIN).withUsername(VALID_USERNAME_DEMO_ONE).build();
        assertNotEquals(DEMO_ADMIN, editedAdminAccount);
    }

    @Test
    public void account_usernameOnly_equals() {
        Account accountDemoOne = new Account(DEMO_ONE.getUsername());
        Account accountDemoTwo = new Account(DEMO_TWO.getUsername());

        // same object -> returns true
        assertEquals(accountDemoOne, accountDemoOne);

        assertNotEquals(accountDemoOne, accountDemoTwo);

        // null -> returns false
        assertNotEquals(null, accountDemoOne);

        // different type -> returns false
        assertNotEquals(5, accountDemoOne);
    }

    @Test
    public void hashcode() {
        final Account adminAccount = new AccountBuilder(DEMO_ADMIN).build();
        final Account demoAccount = new AccountBuilder(DEMO_ONE).build();

        // same values -> returns same hashcode
        assertEquals(adminAccount.hashCode(), adminAccount.hashCode());

        // different values -> returns different hashcode
        assertNotEquals(adminAccount.hashCode(), demoAccount.hashCode());
    }
}
