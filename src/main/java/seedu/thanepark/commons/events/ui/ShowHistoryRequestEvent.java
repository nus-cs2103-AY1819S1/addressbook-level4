package seedu.thanepark.commons.events.ui;

import seedu.thanepark.commons.events.BaseEvent;
import seedu.thanepark.commons.util.FilePathToUrl;

/**
 * An event requesting to view the help page.
 */
public class ShowHistoryRequestEvent extends BaseEvent {
    private final FilePathToUrl reportFilePath;

    /**
     * Creates an event requesting for command history html window.
     */
    public ShowHistoryRequestEvent(FilePathToUrl reportFilePath) {
        this.reportFilePath = reportFilePath;
    }

    public FilePathToUrl getReportFilePath() {
        return reportFilePath;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
