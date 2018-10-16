package seedu.address.model.filereader.exceptions;

/**
 * Signals that the file reader is reading from an empty file
 */
public class EmptyFileException extends RuntimeException {
    public EmptyFileException() {
        super("File is empty, nothing to import");
    }
}