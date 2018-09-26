package seedu.address.model.exceptions;

public class NonExistantUserException extends IllegalArgumentException {
    public NonExistantUserException() {
        super("User does not exist.");
    }
}
