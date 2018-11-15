package seedu.address.model.person.timetable;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigInteger;
import java.util.Base64;

import seedu.address.model.Entity;

/**
 * Represents a timetable in the address book. Guarantees: details are present
 */
public class Timetable extends Entity {

    // Identity fields
    private final String filePath;
    private final String timetableString;

    // create timetable data
    private final TimetableData matrix;


    /**
     * Construct a timetable using the timetableString if option is 1 Construct a timetable using
     * the timetable csv file if option is 2
     */
    public Timetable(String filePath,
        String timetableString, int option, String day, String timing, String message) {
        requireAllNonNull(filePath, option);
        this.filePath = filePath;
        this.matrix = new TimetableData(this.filePath, timetableString,
            option, day, timing, message);
        this.timetableString = generateTimetableDataString();
    }

    /**
     * @return a timetable String for the ui
     */
    public String getTimetableAsString() {
        String timetableString = "";
        String[][] timetableMatrix = this.matrix.getTimetable();
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                //@@author souless94 -reused
                // Solution on converting string to hex string below gotten from Laurence Gonsalves
                // from https://stackoverflow.com/questions/923863/converting-a-string-to-hexadecimal-in-java

                byte[] bytes = timetableMatrix[i][j].getBytes();
                String data = new BigInteger(bytes).toString(16);
                //@@author
                if (i == matrix.getRows() - 1 && j == matrix.getColumns() - 1) {
                    timetableString += data;
                } else {
                    timetableString += data + ",";
                }
            }
        }
        return timetableString;
    }


    /**
     * @return a timetable string for the xml storage
     */
    public String getTimetableDataString() {
        return this.timetableString;
    }

    /**
     * Generate a timetable string for the xml storage
     */
    public String generateTimetableDataString() {
        String timetableDataString = "";
        String[][] timetableMatrix = this.matrix.getTimetable();
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (j == matrix.getColumns() - 1) {
                    timetableDataString += Base64.getEncoder()
                        .encodeToString(timetableMatrix[i][j].getBytes());
                } else {
                    timetableDataString += Base64.getEncoder()
                        .encodeToString(timetableMatrix[i][j].getBytes()) + ",";
                }
            }
            timetableDataString += "\n";
        }
        return timetableDataString;
    }

    public TimetableData getTimetable() {
        return this.matrix;
    }

    /**
     * download timetable to the given location
     */
    public void downloadTimetableAsCsv() {
        String filepath = this.filePath;
        this.matrix.downloadTimetableDataAsCsv(filepath);
    }

    public boolean isValid() {
        return isCorrectSize() && hasCorrectFirstRowsAndColumns();
    }

    public boolean isCorrectSize() {
        return this.matrix.isCorrectSize();
    }

    public boolean hasCorrectFirstRowsAndColumns() {
        return this.matrix.hasCorrectFirstRowsAndColumns();
    }

    public String[][] getTimetableMatrix() {
        return this.matrix.getTimetable();
    }


    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timetable)) {
            return false;
        }
        Timetable otherTimetable = (Timetable) other;
        return otherTimetable.equals(other);
    }

}
