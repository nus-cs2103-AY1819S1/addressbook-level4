package seedu.thanepark.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.thanepark.testutil.EventsUtil.postNow;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;

import seedu.thanepark.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.thanepark.ui.browser.BrowserPanel;
import seedu.thanepark.ui.browser.HelpWindow;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ACCELERATOR);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = HelpWindow.SHORT_HELP_FILE_PATH.filePathToUrl();
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a ride
        postNow(selectionChangedEventStub);
        URL expectedRideUrl = BrowserPanel.RIDE_PAGE_PATH.filePathToUrl();

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRideUrl, browserPanelHandle.getLoadedUrl());
    }
}
