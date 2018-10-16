package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHistoryRequestEvent extends BaseEvent {
    private final String reportName;

    /**
     * Creates an event requesting for command history html window.
     */
    public ShowHistoryRequestEvent(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
