package seedu.restaurant.commons.events.ui;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.commons.events.ui.sales.DisplayRankingEvent;

public class DisplayRankingEventTest {
    private Map<String, String> ranking;

    @Before
    public void setUp() {
        ranking = new LinkedHashMap<>();
        ranking.put("abc", "def");
        ranking.put("123", "456");
        ranking.put("abc", "456");
        ranking.put("123", "def");
    }

    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayRankingEvent(ranking);
        assertEquals("DisplayRankingEvent", event.toString());
    }

    @Test
    public void getRankingToDisplay_success() {
        DisplayRankingEvent event = new DisplayRankingEvent(ranking);
        assertEquals(event.getRankingToDisplay(), ranking);
    }
}
