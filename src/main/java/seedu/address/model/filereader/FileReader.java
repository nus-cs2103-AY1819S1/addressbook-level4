package seedu.address.model.filereader;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a FileReader.
 * Guarantees: file path is present and not null, validated, immutable.
 */
public class FileReader {
    private final File csvFile;
    private final FilePath csvFilePath;

    private int nameIndex = -1;
    private int phoneIndex = -1;

    public FileReader(FilePath csvFilePath) {
        requireAllNonNull(csvFilePath);
        this.csvFilePath = csvFilePath;
        this.csvFile = new File(csvFilePath.toString());
    }

    public FilePath getCsvFilePath() { return csvFilePath; }

    public int getNameIndex() { return nameIndex; }

    public int getPhoneIndex() { return phoneIndex; }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FileReader)) {
            return false;
        }

        FileReader otherFileReader = (FileReader) other;
        return otherFileReader.getCsvFilePath().equals(getCsvFilePath())
                && otherFileReader.getNameIndex() == getNameIndex()
                && otherFileReader.getPhoneIndex() == getPhoneIndex();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(csvFilePath, nameIndex, phoneIndex);
    }

    @Override
    public String toString() {
        return csvFilePath.toString();
    }

}
