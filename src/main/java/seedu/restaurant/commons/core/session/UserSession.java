package seedu.restaurant.commons.core.session;

import seedu.restaurant.model.account.Account;

//@@author AZhiKai
/**
 * Since this is a local desktop application which may or may not work with internet connection, we assume the
 * multiplicity of 0...1 user will be logged in at any time. Thus, it does not make sense to create a list of user
 * sessions as it is impossible in this project's context.
 */
public class UserSession {

    private static boolean isAuthenticated = false;
    private static Account account;

    // This class should not be instantiated.
    private UserSession() {
        throw new AssertionError("UserSession should not be instantiated.");
    }

    /**
     * Stores this {@code Account} info as part of this session.
     *
     * @param acc logged in for this session.
     */
    public static void create(Account acc) {
        if (!isAuthenticated) {
            isAuthenticated = true;
            account = acc;
        }
    }

    /**
     * Logs out of this account which releases this session.
     */
    public static void destroy() {
        isAuthenticated = false;
        account = null;
    }

    /**
     * Updates the session with the new account info, such as updating of account password.
     *
     * @param acc that has been updated.
     */
    public static void update(Account acc) {
        if (isAuthenticated) {
            account = acc;
        }
    }

    /**
     * Checks if this session exists.
     *
     * @return true if this session exists. Otherwise, false.
     */
    public static boolean isAuthenticated() {
        return isAuthenticated && account != null;
    }

    /**
     * Gets the account that is logged in for this session.
     *
     * @return a {@code Account} object.
     */
    public static Account getAccount() {
        return account;
    }
}
