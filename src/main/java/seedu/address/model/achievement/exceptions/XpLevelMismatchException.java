package seedu.address.model.achievement.exceptions;

public class XpLevelMismatchException extends RuntimeException {
    public XpLevelMismatchException() {
        super("Xp value and level do not match.");
    }
}

