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
 * Contains methods to export patient related data.
 */
public class ExportPatients {

    private static final String FILE_NAME = "ClinicIO_patientdata.csv";

    /**
     * Exports patients' personal information to a csv file.
     */
    public static String exportPatients(ObservableList<Patient> patients) {
        List<String> lines = new ArrayList<>();

        for (Patient patient : patients) {
            String name = patient.getName().toString();
            String address = patient.getAddress().toString();
            String phone = patient.getPhone().toString();
            String email = patient.getEmail().toString();

            String line = getCsvRow(name, address, phone, email);
            lines.add(line);
        }

        if (lines.size() < 1) {
            return "No patient data found.";
        }

        try {
            ExportUtil.writeLines(new FileWriter(FILE_NAME), lines);
            return "All patient data successfully exported.";
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            return "An error ocurred while exporting patient data.";
        }
    }

    /**
     * @param values A variable length array of Strings, each denoting a value for a csv column.
     * @return A single csv row.
     */
    public static String getCsvRow(String... values) {
        return String.join(", ", values);
    }
}
