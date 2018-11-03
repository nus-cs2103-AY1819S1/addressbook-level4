package seedu.restaurant.model.account;

import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author AZhiKai
/**
 * Represents an account. Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    private Username username;
    private Password password;

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    /**
     * Only username is required.
     */
    public Account(Username username) {
        requireAllNonNull(username);
        this.username = username;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if both account have the same username.
     */
    public boolean isSameUsername(Account otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null && otherAccount.getUsername().equals(getUsername());
    }

    /**
     * Returns true if both account have the same identity and data fields. This defines a stronger notion of equality
     * between two account.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(getUsername())
                && otherAccount.getPassword().equals(getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        if (password == null) {
            return Objects.hash(username);
        }
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return getUsername().toString();
    }
}
