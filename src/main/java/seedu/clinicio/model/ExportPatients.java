package seedu.clinicio.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.util.ExportUtil;

/**
 *
 */
public class ExportPatients {

    private static final String FILE_NAME = "ClinicIO_patientdata.csv";

    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    /**
     *
     */
    public static String execute(ObservableList<Patient> patients) {
        List<String> lines = new ArrayList<>();

        for (Patient patient : patients) {
            String name = patient.getName().toString();
            String address = patient.getAddress().toString();
            String phone = patient.getPhone().toString();
            String email = patient.getEmail().toString();

            String line = getLine(name, address, phone, email);
            lines.add(line);
        }

        try {
            ExportUtil.writeLines(new FileWriter(FILE_NAME), lines);
            return "All patient data successfully exported.";
        } catch (Exception IOException) {
            // log error
            return "An error ocurred while exporting patient data.";
        }
    }

    /**
     *
     * @param values
     * @return
     */
    public static String getLine(String... values) {
        return String.join(", ", values);
    }
}
