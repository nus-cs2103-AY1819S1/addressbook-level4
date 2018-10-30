package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.patient.Patient;

/**
 *
 */
public class PatientStatistics extends Statistics {

    private ObservableList<Patient> patients;

    public PatientStatistics() {
        this.patients = FXCollections.observableArrayList();
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public void computeSummaryData() {

    }

    @Override
    public void computeVisualizationData() {

    }
}
