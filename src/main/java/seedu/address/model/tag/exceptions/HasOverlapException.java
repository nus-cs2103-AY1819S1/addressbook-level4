package seedu.address.model.tag.exceptions;

/**
 * Signals that the intended TagMap toAdd contains some overlap
 * with this TagMap.
 */
public class HasOverlapException extends RuntimeException {
    public HasOverlapException() {
        super("There is an overlap within the TagMap you are attempting to add");
    }
}
