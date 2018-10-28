package seedu.address.model.analytics;

//@@author arsalanc-v2

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.print.Doc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.analytics.data.Tuple;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.appointment.Date;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Id;
import seedu.address.model.doctor.Password;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;

//@@author arsalanc-v2

/**
 * Responsible for all statistics related to Doctors.
 */
public class DoctorStatistics extends Statistics {

    private final String SUMMARY_TITLE_1 = "Average number of consultations per doctor";

    private ObservableList<Doctor> doctors;
    private ObservableList<Consultation> consultations;
    private ObservableList<Patient> patients;

    public DoctorStatistics() {
        doctors = FXCollections.observableArrayList();
        consultations = FXCollections.observableArrayList();
        patients = FXCollections.observableArrayList();
        initializeSummaryValues(SUMMARY_TITLE_1, DEFAULT_SUMMARY_TEXTS);
    }

    public void setDoctors(ObservableList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }

    /**
     *
     */
    @Override
    public void computeSummaryStatistics() {
        List<Date> consultationDates = consultations.stream()
            .map(consultation -> consultation.getConsultationDate())
            .collect(Collectors.toList());

        // calculate consultation numbers
        int consultationsToday = DateTimeCount.today(consultationDates);
        int consultationsWeek = DateTimeCount.currentWeek(consultationDates);
        int consultationsMonth = DateTimeCount.currentMonth(consultationDates);
        int consultationsYear = DateTimeCount.currentYear(consultationDates);

        int numberOfDoctors = doctors.size();

        if (numberOfDoctors > 0) {
            // calculate averages
            int consultationsTodayPerDoctor = consultationsToday / numberOfDoctors;
            int consultationsWeekPerDoctor = consultationsWeek / numberOfDoctors;
            int consultationsMonthPerDoctor = consultationsMonth / numberOfDoctors;
            int consultationsYearPerDoctor = consultationsYear / numberOfDoctors;

            List<Integer> values = Arrays.asList(consultationsTodayPerDoctor, consultationsWeekPerDoctor,
                consultationsMonthPerDoctor, consultationsYearPerDoctor);

            // update store of calculated values
            statData.updateSummary(SUMMARY_TITLE_1, DEFAULT_SUMMARY_TEXTS, values);
        }
    }

    @Override
    public void computeVisualizationStatistics() {
//        doctors.add(new Doctor(new Id(1), new Name("a"), new Password("a", true)));
//        doctors.add(new Doctor(new Id(1), new Name("b"), new Password("b", true)));
//        doctors.add(new Doctor(new Id(1), new Name("c"), new Password("c", true)));

        plotPreferencesPerDoctorCount();
    }

    /**
     * Computes the number of consultations per doctor.
     */
    public Map<String, Integer> consultationsPerDoctor() {
        return consultations.stream()
            .map(consultation -> consultation.getDoctor())
            // count the number of preferences for each doctor
            .collect(groupingBy(Function.identity(), summingInt(doctor -> 1)))
            .entrySet()
            .stream()
            // convert keys from {@code Doctor} to the {@code String} name
            .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().getName().toString(), entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<String> getAllDoctorNames() {
        return doctors.stream()
            .map(doctor -> doctor.getName().toString())
            .collect(Collectors.toList());
    }

    /**
     *
     */
    public void plotPreferencesPerDoctorCount() {
        List<Tuple<String, Integer>> preferencesDataPoints = getTupleDataPointsCategorical(preferencesPerDoctorCount());

        statData.addCategoricalVisualization("doctorPreferences", ChartType.HORIZONTAL_BAR, "Number of patient " +
            "preferences for each doctor", "Number of patients", "Doctor", getAllDoctorNames(),
            Arrays.asList(preferencesDataPoints), Arrays.asList(""));
    }

    /**
     *
     * @return
     */
    public Map<String, Integer> preferencesPerDoctorCount() {
        Map<String, Integer> prefCounts = new HashMap<String, Integer>();
        for (Doctor doctor : doctors) {
            long count = patients.stream()
                .filter(patient -> patient.getPreferredDoctor().equals(doctor))
                .count();

            prefCounts.put(doctor.getName().toString(), toIntExact(count));
        }

        return prefCounts;
    }
}
