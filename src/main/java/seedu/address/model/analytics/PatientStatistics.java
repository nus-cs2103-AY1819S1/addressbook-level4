package seedu.address.model.analytics;

//@@author arsalanc-v2

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.patient.Patient;

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
    public void computeSummaryStatistics() {
//        patients.stream()
//            .map(patient -> patient);
    }

    @Override
    public void computeVisualizationStatistics() {

    }
}
