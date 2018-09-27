package seedu.address.model.exceptions;

import seedu.address.model.user.Username;

/**
 * Represents an error where a user with the given username already exists in the model is encountered.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(Username username) {
        super("The user \"" + username + "\" already exists exist.");
    }
}
