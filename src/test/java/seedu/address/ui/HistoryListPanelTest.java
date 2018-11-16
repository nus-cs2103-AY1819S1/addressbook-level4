package seedu.address.ui;

//@@author chivent
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.HistoryListPanelHandle;

import seedu.address.commons.events.ui.HistoryUpdateEvent;

/**
 * Contains tests for editing list of {@code HistoryListPanel}.
 */
public class HistoryListPanelTest extends GuiUnitTest {

    private static final ArrayList<String> TRANSFORMATION_SAMPLE = new ArrayList<>(
            Arrays.asList("first", "second", "third", "fourth"));

    private HistoryListPanelHandle historyListPanelHandle;

    @Test
    public void handleHistoryUpdateEvent() {
        initUi();
        postNow(new HistoryUpdateEvent(TRANSFORMATION_SAMPLE));
        assertListMatch();
        assertEquals(TRANSFORMATION_SAMPLE.size(), historyListPanelHandle.getItems().size());

        TRANSFORMATION_SAMPLE.remove(2);
        postNow(new HistoryUpdateEvent(TRANSFORMATION_SAMPLE));
        assertListMatch();
        assertEquals(TRANSFORMATION_SAMPLE.size(), historyListPanelHandle.getItems().size());
    }

    /**
     * Ensures that the lists from {@code historyListView} and {@code sampleList} match after changes
     */
    private void assertListMatch() {
        assertEquals(TRANSFORMATION_SAMPLE, historyListPanelHandle.getItems());
    }

    /**
     * Initializes {@code historyListPanelHandle} with a {@code HistoryListPanel} and fills {@code historyListView}
     */
    private void initUi() {
        HistoryListPanel historyListPanel = new HistoryListPanel();
        uiPartRule.setUiPart(historyListPanel);
        historyListPanelHandle = new HistoryListPanelHandle(historyListPanel.getRoot());
    }
}
