package seedu.address.model.portation;

/**
 * Supported import file types.
 */
public enum SupportedImportFileTypes {
    PLAINTEXT ("text/plain");

    private final String fileType;

    SupportedImportFileTypes(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return fileType;
    }
}
