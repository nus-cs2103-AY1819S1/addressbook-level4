package seedu.clinicio.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.util.ExportUtil;

//@@author arsalanc-v2

/**
 * Contains methods to export patient related data to csv files.
 */
public class ExportPatients {

    private static final String FILE_NAME = "ClinicIO_patientdata.csv";
    private static final String MESSAGE_SUCCESS = "All patient data successfully exported.";
    private static final String MESSAGE_FAILURE = "An error ocurred while exporting patient data.";
    private static final String MESSAGE_EMPTY = "No patient data found.";

    /**
     * Exports patients' personal information to a csv file.
     */
    public static String exportPatients(ObservableList<Patient> patients) {
        List<String> rows = new ArrayList<>();

        for (Patient patient : patients) {
            String name = patient.getName().toString();
            String address = patient.getAddress().toString();
            String phone = patient.getPhone().toString();
            String email = patient.getEmail().toString();

            String row = getCsvRow(name, address, phone, email);
            rows.add(row);
        }

        return execute(rows);
    }

    /**
     * Exports csv rows to a file.
     * @param rows A list of Strings, each a csv row.
     */
    private static String execute(List<String> rows) {
        if (rows.size() < 1) {
            return MESSAGE_EMPTY;
        }

        try {
            ExportUtil.writeLines(new FileWriter(FILE_NAME), rows);
            return MESSAGE_SUCCESS;
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            return MESSAGE_FAILURE;
        }
    }

    /**
     * @param values A variable length array of Strings, each denoting a csv column value for a row.
     * @return A single csv row.
     */
    public static String getCsvRow(String... values) {
        return String.join(", ", values);
    }
}
