package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import static org.junit.Assert.assertEquals;

import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.analytics.data.CircularList;
import seedu.clinicio.model.analytics.data.StatData;
import seedu.clinicio.model.analytics.data.SummaryData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;
import seedu.clinicio.model.analytics.util.DateUtil;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;

/**
 * Test analytics functionality involving model manager, appointments, doctors and consultations.
 * Assumes DateUtil methods are accurate.
 */
public class AnalyticsIntegrationTest {

    private Patient[] patients = {ALEX, BRYAN};

    /**
     * Tests data for displaying appointment statistics.
     * Assumes 24 appointment slots are available in a day.
     */
    @Test
    public void apptStatsTest() {
        ModelManager modelManager = new ModelManager();

        // add appointments to model manager
        for (Appointment appt : generateAppointments()) {
            modelManager.addAppointment(appt);
        }

        // get all appointment statistics data
        StatData statData = modelManager.retrieveAnalytics(StatisticType.APPOINTMENT);

        // get summary data
        SummaryData summaryData = statData.getSummaryData();
        List<Tuple<String, Integer>> summaryElements = summaryData.getSummaryElements();

        // test summary data
        assertEquals(summaryElements.get(0).getKey(), "Today");
        assertEquals(summaryElements.get(0).getValue(), (Integer) 1);
        assertEquals(summaryElements.get(1).getKey(), "This Week");
        assertEquals(summaryElements.get(1).getValue(), (Integer) 7);
        int numMonthDays = LocalDate.now().lengthOfMonth();
        assertEquals(summaryElements.get(2).getKey(), "This Month");
        assertEquals(summaryElements.get(2).getValue(), (Integer) numMonthDays);
        int numYearDays = LocalDate.now().lengthOfYear();
        assertEquals(summaryElements.get(3).getKey(), "This Year");
        assertEquals(summaryElements.get(3).getValue(), (Integer) numYearDays);

        // get visualization data
        CircularList<VisualizationData> visualizationData = statData.getVisualizationData();
        // test the first appointment statistics chart
        VisualizationData apptSupplyDemand = visualizationData.getNext();
        // this should be a stacked bar chart - 2 groups of data
        assertEquals(apptSupplyDemand.getDataGroups().size(), 2);
        List<Tuple> availableAppts = (List<Tuple>) apptSupplyDemand.getDataGroups().get(1);
        for (Tuple dataPoint : availableAppts) {
            // only one appointment was scheduled for each day in the year
            // so each day next week should have 23 slots left
            assertEquals(dataPoint.getValue(), 23);
        }
    }

    /**
     * Tests data for displaying doctor statistics.
     */
    @Test
    public void doctorStatsTest() {
        ModelManager modelManager = new ModelManager();

        // fill model manager
        modelManager.addPatient(ALEX);
        modelManager.addPatient(BRYAN);
        modelManager.addStaff(ADAM);
        modelManager.addStaff(BEN);
        ALEX.setPreferredDoctor(ADAM);
        BRYAN.setPreferredDoctor(BEN);

        int numberDoctors = 2;
        int consultationsPerDay = 2;
        // add consultations to model manager
        for (Consultation consultation : generateConsultations(consultationsPerDay)) {
            modelManager.addConsultation(consultation);
        }

        // get all doctor statistics data
        StatData statData = modelManager.retrieveAnalytics(StatisticType.DOCTOR);

        // get summary data
        SummaryData summaryData = statData.getSummaryData();
        List<Tuple<String, Integer>> summaryElements = summaryData.getSummaryElements();

        // test summary data
        assertEquals(summaryElements.get(0).getKey(), "Today");
        assertEquals((long) summaryElements.get(0).getValue(), (long) consultationsPerDay / numberDoctors);
        assertEquals(summaryElements.get(1).getKey(), "This Week");
        assertEquals((long) summaryElements.get(1).getValue(), (long) consultationsPerDay * 7 / numberDoctors);
        int numMonthDays = LocalDate.now().lengthOfMonth();
        assertEquals(summaryElements.get(2).getKey(), "This Month");
        assertEquals((long) summaryElements.get(2).getValue(), (long) consultationsPerDay * numMonthDays
            / numberDoctors);
        int numYearDays = LocalDate.now().lengthOfYear();
        assertEquals(summaryElements.get(3).getKey(), "This Year");
        assertEquals((long) summaryElements.get(3).getValue(), (long) consultationsPerDay * numYearDays
            / numberDoctors);

        // get visualization data
        CircularList<VisualizationData> visualizationData = statData.getVisualizationData();

        // test the first doctor statistics chart's data points
        VisualizationData doctorPrefs = visualizationData.getNext();
        assertEquals(doctorPrefs.getDataGroups().size(), 1);
        List<Tuple> doctorPrefCounts = (List<Tuple>) doctorPrefs.getDataGroups().get(0);
        // there should be one patient preference for each doctor
        assertEquals(doctorPrefCounts.get(0).getKey(), ADAM.getName().toString());
        assertEquals(doctorPrefCounts.get(0).getValue(), 1);
        assertEquals(doctorPrefCounts.get(1).getKey(), BEN.getName().toString());
        assertEquals(doctorPrefCounts.get(1).getValue(), 1);

        // test the second doctor statistics chart's data points
        VisualizationData doctorConsultationsCounts = visualizationData.getNext();
        assertEquals(doctorConsultationsCounts.getDataGroups().size(), 1);
        List<Tuple> doctorConsultationCounts = (List<Tuple>) doctorConsultationsCounts.getDataGroups().get(0);
        assertEquals(doctorConsultationCounts.get(0).getKey(), ADAM.getName().toString());
        assertEquals(doctorConsultationCounts.get(0).getValue(), numYearDays);
        assertEquals(doctorConsultationCounts.get(1).getKey(), BEN.getName().toString());
        assertEquals(doctorConsultationCounts.get(1).getValue(), numYearDays);
    }

    /**
     * Creates a single appointment for each day in the current year.
     * Alternates patients between days.
     * Appointment times are fixed.
     */
    private List<Appointment> generateAppointments() {
        int patientToPick = 0;
        int year = Year.now().getValue();
        List<Appointment> appts = new ArrayList<>();
        Time time = new Time(14, 30);
        for (Date date : DateUtil.getCurrentYearDates()) {
            Appointment appt = new Appointment(date, time, patients[patientToPick], 1);
            appts.add(appt);
            if (patientToPick == 0) {
                patientToPick = 1;
            } else {
                patientToPick = 0;
            }
        }

        return appts;
    }

    /**
     * Creates consultations for each day in the current year.
     * @param consultationsPerDay the number of consultations to create for each day.
     * Each day will only have one of the two patients so patients are alternated between days.
     * However, doctors are alternated within each day.
     * Each consultation has a fixed time.
     */
    private List<Consultation> generateConsultations(int consultationsPerDay) {
        int patientToPick = 0;
        Time time = new Time(15, 10);
        List<Consultation> consultations = new ArrayList<>();
        for (Date date : DateUtil.getCurrentYearDates()) {
            for (int i = 0; i < consultationsPerDay; i++) {
                Consultation consultation = new Consultation(patients[patientToPick], date, time);
                // alternate doctors
                if (i % 2 == 0) {
                    consultation.updateDoctor(ADAM);
                } else {
                    consultation.updateDoctor(BEN);
                }
                consultations.add(consultation);
            }
            // alternate patients
            if (patientToPick == 0) {
                patientToPick = 1;
            } else {
                patientToPick = 0;
            }
        }

        return consultations;
    }
}
