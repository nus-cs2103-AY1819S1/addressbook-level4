package seedu.address.ui;

//@@author chivent

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.LayerListPanelHandle;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.LayerUpdateEvent;
/**
 * Contains tests for editing list of {@code HistoryListPanel}.
 */
public class LayerListPanelTest extends GuiUnitTest {

    private static final ArrayList<String> LAYER_SAMPLE = new ArrayList<>(
            Arrays.asList("first", "second", "third", "fourth"));

    private LayerListPanelHandle layerListPanelHandle;

    @Test
    public void handleLayerUpdateEvent() {
        initUi();
        postNow(new LayerUpdateEvent(LAYER_SAMPLE, Index.fromZeroBased(1)));
        assertListMatch();
        assertEquals(LAYER_SAMPLE.size(), layerListPanelHandle.getItems().size());

        LAYER_SAMPLE.remove(2);
        postNow(new LayerUpdateEvent(LAYER_SAMPLE, Index.fromZeroBased(2)));
        assertListMatch();
        assertEquals(LAYER_SAMPLE.size(), layerListPanelHandle.getItems().size());
    }

    /**
     * Ensures that the lists from {@code layerListView} and {@code LAYER_SAMPLE} match after changes
     */
    private void assertListMatch() {
        assertEquals(LAYER_SAMPLE, layerListPanelHandle.getItems());
    }

    /**
     * Initializes {@code layerListPanelHandle} with a {@code LayerListPanel} and fills {@code layerListView}
     */
    private void initUi() {
        LayerListPanel layerListPanel = new LayerListPanel();
        uiPartRule.setUiPart(layerListPanel);
        layerListPanelHandle = new LayerListPanelHandle(layerListPanel.getRoot());
    }
}
