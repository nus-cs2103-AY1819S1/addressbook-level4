package seedu.scheduler.ui;

import static seedu.scheduler.testutil.TypicalEvents.MA2101_JANUARY_1_2018_YEARLY;

import org.junit.Before;

import guitests.guihandles.BrowserPanelHandle;
import seedu.scheduler.commons.events.ui.EventPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private EventPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new EventPanelSelectionChangedEvent(MA2101_JANUARY_1_2018_YEARLY);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }
    /**
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a event
        postNow(selectionChangedEventStub);
        URL expectedEventUrl = new URL(BrowserPanel.CALENDER_PAGE_URL
                + MA2101_JANUARY_1_2018_YEARLY.getEventName().value.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedEventUrl, browserPanelHandle.getLoadedUrl());
    }
    **/
}
