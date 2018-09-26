package seedu.address.model.exceptions;

import seedu.address.model.user.Username;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException(Username username) {
        super("The user \"" + username + "\" already exists exist.");
    }
}
