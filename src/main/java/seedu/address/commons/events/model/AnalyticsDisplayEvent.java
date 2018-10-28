package seedu.address.commons.events.model;

import java.util.List;
import java.util.Map;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.analytics.StatData;
import seedu.address.model.analytics.StatisticType;

/**
 *
 */
public class AnalyticsDisplayEvent extends BaseEvent {

    private final String MESSAGE = "This is the %s statistics display event";
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
