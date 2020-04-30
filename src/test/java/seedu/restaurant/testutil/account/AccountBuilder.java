package seedu.restaurant.testutil.account;

import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Name;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai

/**
 * A utility class to help with building {@code Account} objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_USERNAME = "azhikai";
    public static final String DEFAULT_PASSWORD = "1122qq!@#";
    public static final String DEFAULT_NAME = "Ang Zhi Kai";

    private Username username;
    private Password password;
    private Name name;

    public AccountBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the AccountBuilder with the data of {@code accountToCopy}.
     */
    public AccountBuilder(Account accountToCopy) {
        username = accountToCopy.getUsername();
        password = accountToCopy.getPassword();
        name = accountToCopy.getName();
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Account} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Account} that we are building.
     */
    public AccountBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Account build() {
        return new Account(username, password, name);
    }
}
