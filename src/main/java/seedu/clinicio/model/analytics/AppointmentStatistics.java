package seedu.clinicio.model.analytics;

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.consultation.Consultation;

//@@author arsalanc-v2

/**
 * Responsible for all statistics related to appointments.
 */
public class AppointmentStatistics extends Statistics {

    private static final int NUM_APPOINTMENTS_DAY = 24;

    private ObservableList<Appointment> appointments;
    private ObservableList<Consultation> consultations;

    private final String SUMMARY_TITLE_1 = "Number of appointments";

    public AppointmentStatistics() {
        this.appointments = FXCollections.observableArrayList();
    }

    /**
     * Enables the latest list of appointments to be kept.
     * @param appointments An updated list of appointments.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     *
     * @param consultations
     */
    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    /**
     * @return the total number of appointments.
    */
    private int getNumberOfAppointments() {
        return appointments.size();
    }

    /**
     * @return the total number of cancelled appointments.
     */
    private int getNumberOfCancelledAppointments() {
        long cancelledCount = appointments.stream()
            .filter(appt -> appt.isCancelled())
            .count();

        return toIntExact(cancelledCount);
    }

    /**
     * @return the total number of approved appointments.
     * Depends on the fact that there are only two appointment statuses.
     */
    private int getNumberOfApprovedAppointments() {
       return getNumberOfAppointments() - getNumberOfCancelledAppointments();
    }

    /**
     * @return the total number of follow up appointments.
    */
    private int getNumberOfFollowUpAppointments() {
        long followUpCount = appointments.stream()
            .filter(appt -> appt.getAppointmentType() == 1)
            .count();

        return toIntExact(followUpCount);
    }

    /**
     * Computes the number of appointments for each day of the present week.
    */
    private Map<String, Integer> getNumberOfCurrentWeekAppointments() {
        List<Date> datesOfAppointments = appointments.stream()
                .map(appt -> appt.getAppointmentDate())
                .collect(Collectors.toList());

        return DateTimeCount.eachDayOfCurrentWeek(datesOfAppointments);
    }

    /**
     *
     */
    public void plotAppointmentSupplyDemand() {
        // get the subset of appointments that are scheduled for next week.
        List<Date> scheduledSlotsDates = appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .filter(date -> DateTimeCount.isNextWeek(date))
            .collect(Collectors.toList());

        // get a list of the dates for next week
        List<Date> nextWeekDates = DateTimeCount.getNextWeekDates();
        // get a list of all days next week
        List<String> nextWeekDays = DateTimeCount.getDaysOfWeek();

        List<Date> availableSlotsDates = new ArrayList<>();
        // for each day of next week, find the number of slots not scheduled yet.
        for (Date nextWeekDate : nextWeekDates) {
            // find the number of scheduled slots for a particular day in the following week
            long scheduledSlots = scheduledSlotsDates.stream()
                .filter(scheduledDate -> scheduledDate == nextWeekDate)
                .count();

            // find the number of available slots for a particular day in the following week
            int availableSlots = NUM_APPOINTMENTS_DAY - toIntExact(scheduledSlots);
            availableSlotsDates.addAll(Collections.nCopies(availableSlots, nextWeekDate));
        }

        List<List<Tuple<String, Integer>>> appointmentGroupsData = overNextWeekData(Arrays.asList
            (scheduledSlotsDates, availableSlotsDates));
        List<String> appointmentGroupsLabels = Arrays.asList("scheduled", "available");

        statData.addCategoricalVisualization("apptSupplyDemand", ChartType.STACKED_BAR,
            "Appointments Next Week", "Day of week", "Number of appointments", nextWeekDays,
            appointmentGroupsData, appointmentGroupsLabels);
    }

    /**
     * Updates {@code summaryStatistics} with the latest values.
     */
    @Override
    public void computeSummaryStatistics() {
        List<Date> appointmentDates = appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .collect(Collectors.toList());

        // calculate appointment numbers - TO ABSTRACT
        int appointmentsToday = DateTimeCount.today(appointmentDates);
        int appointmentsWeek = DateTimeCount.currentWeek(appointmentDates);
        int appointmentsMonth = DateTimeCount.currentMonth(appointmentDates);
        int appointmentsYear = DateTimeCount.currentYear(appointmentDates);

        List<Integer> values = Arrays.asList(appointmentsToday, appointmentsWeek, appointmentsWeek, appointmentsMonth);

        // update data with calculated values
        statData.updateSummary(SUMMARY_TITLE_1, DEFAULT_SUMMARY_TEXTS, values);
    }

    @Override
    public void computeVisualizationStatistics() {
        plotAppointmentSupplyDemand();

    }
}
