package seedu.inventory.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.inventory.testutil.EventsUtil.postNow;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.inventory.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.inventory.MainApp;
import seedu.inventory.commons.events.ui.ItemPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private ItemPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ItemPanelSelectionChangedEvent(IPHONE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a item
        postNow(selectionChangedEventStub);
        URL expectedItemUrl = new URL(BrowserPanel.SEARCH_PAGE_URL
                + IPHONE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedItemUrl, browserPanelHandle.getLoadedUrl());
    }
}
