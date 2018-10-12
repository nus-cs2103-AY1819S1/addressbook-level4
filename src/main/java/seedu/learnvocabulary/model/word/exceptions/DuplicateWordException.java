package seedu.learnvocabulary.model.word.exceptions;

/**
 * Signals that the operation will result in duplicate Words (Words are considered duplicates if they have the same
 * identity).
 */
public class DuplicateWordException extends RuntimeException {
    public DuplicateWordException() {
        super("Operation would result in duplicate words");
    }
}
