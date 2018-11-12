package seedu.address.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalUsernameException extends RuntimeException {

    private String username;

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalUsernameException(String message) {
        super("The following username: " + message + " already exists in the system!");
        username = message;
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public IllegalUsernameException(String message, Throwable cause) {
        super("The following username: " + message + " already exists in the system!", cause);
        username = message;
    }

    public String getUsername() {
        return username;
    }
}
