package seedu.address.model.filereader;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import seedu.address.model.filereader.exceptions.EmptyFileException;

/**
 * Represents a FileReader.
 * Guarantees: file path is present and not null, validated, immutable.
 */
public class FileReader {
    public static final String CSV_HEADER_NAME = "Name";
    public static final String CSV_HEADER_PHONE = "Phone 1 - Value";

    private final File csvFile;
    private final FilePath csvFilePath;
    private boolean isValidFile = false;

    private int nameIndex = -1;
    private int phoneIndex = -1;
    private ArrayList<String> contacts = new ArrayList<>();

    public FileReader(FilePath csvFilePath) {
        requireAllNonNull(csvFilePath);
        this.csvFilePath = csvFilePath;
        this.csvFile = new File(csvFilePath.toString());
        readImportContactsFile();
    }

    public FilePath getCsvFilePath() {
        return csvFilePath;
    }

    public boolean isValidFile() {
        return isValidFile;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getPhoneIndex() {
        return phoneIndex;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    /**
     * Reads contact information from given csv file.
     */
    public void readImportContactsFile() {
        try {
            Scanner sc = new Scanner(csvFile);
            if (!sc.hasNextLine()) {
                throw new EmptyFileException();
            }
            String header = sc.nextLine();
            String[] parts = header.split(",");
            isValidFile = setIndex(parts);
            if (isValidFile) {
                while (sc.hasNextLine()) {
                    contacts.add(sc.nextLine());
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // will never happen, toImport is validated by parser
        }
    }

    private boolean setIndex(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(CSV_HEADER_NAME)) {
                nameIndex = i;
            }
            if (parts[i].equals(CSV_HEADER_PHONE)) {
                phoneIndex = i;
            }
        }
        // return true if nameIndex and phoneIndex is valid
        return nameIndex != -1 && phoneIndex != -1;
    }

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
