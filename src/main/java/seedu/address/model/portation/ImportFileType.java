package seedu.address.model.portation;

import static seedu.address.model.portation.SupportedImportFileTypes.PLAINTEXT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the type of the import file.
 */
public class ImportFileType {
    private final List<SupportedImportFileTypes> supportedFileTypes;

    private Path importFilePath;

    public ImportFileType(Path importFilePath) {
        this.importFilePath = importFilePath;
        supportedFileTypes = new ArrayList<>(Arrays.asList(PLAINTEXT)); // to support more file types
    }

    /**
     * Returns true if the given file path is a supported file type.
     * Otherwise false if it is not supported in {@link SupportedImportFileTypes}
     * or the file is not readable.
     *
     * @see #isSupportedFileType
     */
    public boolean isFileTypeSupported() {
        try {
            return isSupportedFileType(Files.probeContentType(importFilePath));
        } catch (IOException ioe) {
            return false;
        }
    }

    /**
     * Returns true if the given fileType is supported in {@link SupportedImportFileTypes}.
     */
    private boolean isSupportedFileType(String fileType) {
        for (SupportedImportFileTypes supportedFileType : supportedFileTypes) {
            if ((supportedFileType.toString()).equals(fileType)) {
                return true;
            }
        }
        return false;
    }
}
