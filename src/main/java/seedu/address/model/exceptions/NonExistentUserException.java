package seedu.address.model.exceptions;

import seedu.address.model.user.Username;

//@@author JasonChong96
/**
 * Represents an error where a user with the given username does not exist in the model is encountered.
 */
public class NonExistentUserException extends Exception {
    public NonExistentUserException(Username username, int numUsers) {
        super("The user \"" + username + "\" does not exist. Num of users: " + numUsers);
    }
}
