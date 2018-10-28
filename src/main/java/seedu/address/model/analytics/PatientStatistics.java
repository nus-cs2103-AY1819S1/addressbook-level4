package seedu.address.model.analytics;

//@@author arsalanc-v2

import javafx.collections.ObservableList;
import seedu.address.model.patient.Patient;

/**
 *
 */
public class PatientStatistics extends Statistics {

    private ObservableList<Patient> patients;

    @Override
    public void computeSummaryStatistics() {
        patients.stream()
            .map(patient -> patient);
    }

    @Override
    public void computeVisualizationStatistics() {

    }

//    public void setPatients(ObservableList<Patient> patients) {
//        this.patients = patients;
//    }

    // number of patients
    // get all patients

}
