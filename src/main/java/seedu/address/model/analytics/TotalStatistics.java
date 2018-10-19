package seedu.address.model.analytics;

//import static java.lang.Math.toIntExact;
//import static java.util.stream.Collectors.groupingBy;
//import static java.util.stream.Collectors.summingInt;
//
//import java.util.Map;
//import java.util.function.Function;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;

/**
 * Represents statistics that are total numbers.
 * @@author arsalanc-v2
 */
public class TotalStatistics extends Statistics {

    public static final StatisticType STATISTIC_TYPE = StatisticType.TOTAL;
    public static final int NUMBER_STATISTICS = 3;

    // Different statistics fields.
    private static final String NUMBER_DAYS = STATISTIC_TYPE
            + " number of days of operation (inclusive)";
    private static final String NUMBER_PATIENTS = STATISTIC_TYPE
            + " number of patients to date";
    private static final String NUMBER_MEDICINES = STATISTIC_TYPE
            + " number of medicines prescribed to date";

    private ObservableList<Appointment> appointments;

    // Sets statistics to 0 and stores them in a HashMap.
    public TotalStatistics() {
        super();
        this.statistics.put(NUMBER_DAYS, 0);
        this.statistics.put(NUMBER_PATIENTS, 0);
        this.statistics.put(NUMBER_MEDICINES, 0);
    }

    /**
     * @param appointments An updated list of appointments.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /*
     * return the total number of appointments.

    private int getNumberOfAppointments() {
        return appointments.size();
    }

    /**
     * return the total number of follow up appointments.

    private int getNumberOfFollowUpAppointments() {
        long followUpCount = appointments.stream()
            .filter(appt -> appt.getAppointmentType() == 1)
            .count();

        return toIntExact(followUpCount);
    }

    /**
     * Computes the number of appointments for each day of the present week.

    private Map<String, Integer> getNumberOfCurrentWeekAppointments() {
        Map<String, Integer> appointmentsWeekBreakdown =
            appointments.stream()
                .map(appt -> appt.getAppointmentDate())
                .filter(date -> date.isCurrentWeek())
                .map(date -> date.toString())
                .collect(groupingBy(Function.identity(), summingInt(date -> 1)));

        return appointmentsWeekBreakdown;
    }
    */

    @Override
    public void compute() {
        // placeholder
    }
}
