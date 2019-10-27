package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ExpandedEntryPanelHandle;
import seedu.address.commons.events.ui.UpdateExpandedEntryRequestEvent;

public class ExpandedEntryPanelTest extends GuiUnitTest {
    private static final UpdateExpandedEntryRequestEvent NEW_RESULT_EVENT_STUB =
            new UpdateExpandedEntryRequestEvent(WORK_FACEBOOK); // an entry with empty description
    private static final String EMPTY_STRING = "";


    private ExpandedEntryPanelHandle expandedEntryPanelHandle;

    @Before
    public void setUp() {
        ExpandedEntryPanel expandedEntryPanel = new ExpandedEntryPanel();
        uiPartRule.setUiPart(expandedEntryPanel);

        expandedEntryPanelHandle = new ExpandedEntryPanelHandle(getChildNode(expandedEntryPanel.getRoot(),
                ExpandedEntryPanelHandle.EXPANDED_ENTRY_PANEL_ID));
        // getChildNode returns the node with id EXPANDED_ENTRY_PANEL_ID
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals(EMPTY_STRING, expandedEntryPanelHandle.getText());

        // new result received
        postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(WORK_FACEBOOK.getDescription().toString(), expandedEntryPanelHandle.getText());
    }

}
