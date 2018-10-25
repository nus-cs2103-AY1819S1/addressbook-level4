package seedu.address.model.analytics;

//@@author arsalanc-v2

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.appointment.Date;
import seedu.address.model.doctor.Doctor;

//@@author arsalanc-v2

/**
 *
 */
public class DoctorStatistics extends Statistics {

    private ObservableList<Doctor> doctors;
    private ObservableList<Consultation> consultations;
    private ObservableList<Object> patients;

    public void setDoctors(ObservableList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setPatients(ObservableList<Object> patients) {
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
        // calculate averages
        int consultationsTodayPerDoctor = consultationsToday / numberOfDoctors;
        int consultationsWeekPerDoctor = consultationsWeek / numberOfDoctors;
        int consultationsMonthPerDoctor = consultationsMonth / numberOfDoctors;
        int consultationsYearPerDoctor = consultationsYear / numberOfDoctors;

        // update map with calculated values
        summaryStatistics.put(SUMMARY_TODAY, consultationsTodayPerDoctor);
        summaryStatistics.put(SUMMARY_WEEK, consultationsWeekPerDoctor);
        summaryStatistics.put(SUMMARY_MONTH, consultationsMonthPerDoctor);
        summaryStatistics.put(SUMMARY_YEAR, consultationsYearPerDoctor);
    }

    @Override
    public void computeVisualizationStatistics() {

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

    /**
     *
     * @return
     */
    public Map<String, Integer> preferencesPerDoctorCount() {
        return null;
        //patients.stream()
        //.map(patient -> patient.getDoctorPreference())
        //.collect(groupingBy(Function.identity(), summingInt(doctor -> 1)));
    }
}
