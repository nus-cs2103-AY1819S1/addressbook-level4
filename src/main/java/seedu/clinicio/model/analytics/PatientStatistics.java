package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;

/**
 * Responsible for all statistics related to patients.
 */
public class PatientStatistics extends Statistics {

    private static final String SUMMARY_TITLE = "Number of consultations";

    private List<Patient> patients;
    private List<Consultation> consultations;

    public PatientStatistics() {
        this.patients = new ArrayList<>();
        this.consultations = new ArrayList<>();
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }
    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    /**
     * Calculate the number of non-unique patients (consultations) over various time periods.
     */
    @Override
    public void computeSummaryData() {
        List<Date> consultationDates = consultations.stream()
            .map(consultation -> consultation.getConsultationDate())
            .collect(Collectors.toList());

        List<Integer> consultationSummaryValues = computeSummaryTotals(consultationDates);
        statData.updateSummary(SUMMARY_TITLE, defaultSummaryTexts, consultationSummaryValues);
    }

    /**
     *
     */
    public void plotPatientsOverDayOfWeek() {
    }

    @Override
    public void computeVisualizationData() {

    }
}
