package seedu.learnvocabulary.model.word.exceptions;

/**
 * Signals that the operation is unable to find the specified word.
 */
public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
