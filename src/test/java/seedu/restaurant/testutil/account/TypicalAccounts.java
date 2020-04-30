package seedu.restaurant.testutil.account;

import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_DEMO_THREE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_DEMO_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_THREE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_THREE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.restaurant.model.account.Account;

//@@author AZhiKai

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {

    // Accounts as follow has hashed password when tests on TypicalRestaurantBook is triggered.
    public static final Account DEMO_ADMIN = new AccountBuilder().build();
    public static final Account DEMO_ONE = new AccountBuilder()
            .withUsername(VALID_USERNAME_DEMO_ONE)
            .withPassword(VALID_PASSWORD_DEMO_ONE)
            .withName(VALID_NAME_DEMO_ONE)
            .build();
    public static final Account DEMO_TWO = new AccountBuilder()
            .withUsername(VALID_USERNAME_DEMO_TWO)
            .withPassword(VALID_PASSWORD_DEMO_TWO)
            .withName(VALID_NAME_DEMO_TWO)
            .build();

    // Manually added, not included as list of typical accounts, thus password is still in plain text.
    public static final Account DEMO_THREE = new AccountBuilder()
            .withUsername(VALID_USERNAME_DEMO_THREE)
            .withPassword(VALID_PASSWORD_DEMO_THREE)
            .withName(VALID_NAME_DEMO_THREE)
            .build();

    private TypicalAccounts() {} // prevents instantiation

    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(DEMO_ADMIN, DEMO_ONE, DEMO_TWO));
    }
}
