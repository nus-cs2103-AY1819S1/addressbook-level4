package seedu.address.commons.events.model;

import java.util.Map;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.analytics.StatisticType;

/**
 *
 */
public class AnalyticsDisplayEvent extends BaseEvent {

    private final String MESSAGE = "This is the %s statistics display event";
    private StatisticType type;
    private Map<String, Map<String, Integer>> allStats;

    public AnalyticsDisplayEvent(StatisticType type, Map<String, Map<String, Integer>> allStats) {
        this.type = type;
        this.allStats = allStats;
    }

    public Map<String, Map<String, Integer>> getAllStatistics() {
        return allStats;
    }

    @Override
    public String toString() {
        return String.format(MESSAGE, type);
    }
}
