package seedu.address.model.exceptions;

public class NoUserSelectedException extends Exception {
    public NoUserSelectedException(String desc) {
        super(desc);
    }

    public NoUserSelectedException() {
        super("Not logged into any user.");
    }
}
