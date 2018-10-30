package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;

//@@author arsalanc-v2

/**
 * Responsible for all statistics related to Doctors.
 */
public class DoctorStatistics extends Statistics {

    private final String SUMMARY_TITLE_1 = "Average number of consultations per doctor";

    private ObservableList<Staff> doctors;
    private ObservableList<Consultation> consultations;
    private ObservableList<Patient> patients;

    public DoctorStatistics() {
        doctors = FXCollections.observableArrayList();
        consultations = FXCollections.observableArrayList();
        patients = FXCollections.observableArrayList();
        initializeSummaryValues(SUMMARY_TITLE_1, DEFAULT_SUMMARY_TEXTS);
    }

    public void setDoctors(ObservableList<Staff> doctors) {
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
        plotPreferencesPerDoctorCount();
    }

    /**
     * Computes the number of consultations per doctor.
     */
    public Map<String, Integer> consultationsPerDoctor() {
        Map<String, Integer> consultationCounts = consultations.stream()
            .map(consultation -> consultation.getDoctor())
            // remove non doctor staff
            .filter(staffOptional -> staffOptional.map(staff -> staff.getRole().equals(Role.DOCTOR)).orElse(false))
            // count the number of preferences for each doctor
            .collect(groupingBy(Function.identity(), summingInt(doctor -> 1)))
            .entrySet()
            .stream()
            // ensure the staff are doctors
            .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey()
            // convert the {@code Staff} object to its name
            .map(doctor -> doctor.getName().toString()).orElse("otherstaff"), entry
                .getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // remove non doctor staff
        consultationCounts.remove("otherstaff");
        return consultationCounts;
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
        for (Staff doctor : doctors) {
            long count = patients.stream()
                .filter(patient -> patient.getPreferredDoctor().equals(doctor))
                .count();

            prefCounts.put(doctor.getName().toString(), toIntExact(count));
        }

        return prefCounts;
    }
}
