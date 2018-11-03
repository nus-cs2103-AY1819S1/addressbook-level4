package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.model.analytics.StatisticType;
import seedu.clinicio.model.analytics.data.StatData;

//@@author arsalanc-v2

/**
 * Responsible for triggering the display of a particular class of analytics.
 */
public class AnalyticsDisplayEvent extends BaseEvent {

    private static final String MESSAGE = "This is the %s statistics display event";
    // the type of statistic to display data for
    private StatisticType type;
    // the data for the statistic to be displayed
    private StatData allData;

    public AnalyticsDisplayEvent(StatisticType type, StatData allData) {
        this.type = type;
        this.allData = allData;
    }

    public StatData getAllData() {
        return allData;
    }

    @Override
    public String toString() {
        return String.format(MESSAGE, type);
    }
}
