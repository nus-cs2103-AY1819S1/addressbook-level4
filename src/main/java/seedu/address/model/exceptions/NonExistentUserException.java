package seedu.address.model.exceptions;

import seedu.address.model.user.Username;

public class NonExistentUserException extends IllegalArgumentException {
    public NonExistentUserException(Username username) {
        super("The user \"" + username + "\" does not exist.");
    }
}
