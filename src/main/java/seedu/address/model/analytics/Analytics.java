package seedu.address.model.analytics;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;

/**
 * Wrapper for all analytics functionality.
 * @@author arsalanc-v2
 */
public class Analytics {

    private AverageStatistics averageStatistics;
    private TotalStatistics totalStatistics;

    public Analytics() {
        averageStatistics = new AverageStatistics();
        totalStatistics = new TotalStatistics();
    }

    /**
     * @return The String representation for displaying of average statistics.
     */
    public String getAverageStatistics() {
        return averageStatistics.toString();
    }

    /**
     * @return The String representation for displaying total statistics.
     */
    public String getTotalStatistics() {
        return totalStatistics.toString();
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        totalStatistics.setAppointments(appointments);
    }
}
