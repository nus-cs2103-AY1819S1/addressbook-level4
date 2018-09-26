package seedu.address.model.exceptions;

import seedu.address.model.user.Username;

public class NonExistentUserException extends Exception {
    public NonExistentUserException(Username username) {
        super("The user \"" + username + "\" does not exist.");
    }
}
