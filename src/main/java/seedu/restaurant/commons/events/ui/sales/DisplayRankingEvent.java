package seedu.restaurant.commons.events.ui.sales;

import java.util.Map;

import seedu.restaurant.commons.events.BaseEvent;

/**
 * An event requesting to display the ranking given in the Map
 */
public class DisplayRankingEvent extends BaseEvent {

    private Map<String, String> rankingToDisplay;

    public DisplayRankingEvent(Map<String, String> rankingToDisplay) {
        this.rankingToDisplay = rankingToDisplay;
    }

    public Map<String, String> getRankingToDisplay() {
        return rankingToDisplay;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
