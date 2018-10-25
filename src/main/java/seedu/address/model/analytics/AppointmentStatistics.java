package seedu.address.model.analytics;

import static java.lang.Math.toIntExact;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;

//@@author arsalanc-v2

/**
 * @@author arsalanc-v2
 */
public class AppointmentStatistics extends Statistics {

    private ObservableList<Appointment> appointments;

//    public AppointmentStatistics(ObservableList<Appointment> appointments) {
//        this.appointments = appointments;
//        updateSummaryStatistics();
//    }

    /**
     * @param appointments An updated list of appointments.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
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
    @Override
    public void computeSummaryStatistics() {
        List<Date> appointmentDates = appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .collect(Collectors.toList());

        // calculate appointment numbers
        int appointmentsToday = DateTimeCount.today(appointmentDates);
        int appointmentsWeek = DateTimeCount.currentWeek(appointmentDates);
        int appointmentsMonth = DateTimeCount.currentMonth(appointmentDates);
        int appointmentsYear = DateTimeCount.currentYear(appointmentDates);

        // update map with calculated values
        summaryStatistics.put(SUMMARY_TODAY, appointmentsToday);
        summaryStatistics.put(SUMMARY_WEEK, appointmentsWeek);
        summaryStatistics.put(SUMMARY_MONTH, appointmentsMonth);
        summaryStatistics.put(SUMMARY_YEAR, appointmentsYear);
    }

    @Override
    public void computeVisualizationStatistics() {

    }
}
