package seedu.address.commons.exceptions;

/**
 * Represents an error during conversion of data to Markdown.
 */
public class MarkdownProcessingException extends java.io.IOException {
    public MarkdownProcessingException(String msg) {
        super(msg);
    }

    @Override public String toString() {
        return getClass().getName() + ": " + getMessage();
    }
}

