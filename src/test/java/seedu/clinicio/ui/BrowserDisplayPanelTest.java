package seedu.clinicio.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.clinicio.testutil.EventsUtil.postNow;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.ui.BrowserDisplayPanel.DEFAULT_PAGE;
import static seedu.clinicio.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.clinicio.MainApp;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;

public class BrowserDisplayPanelTest extends GuiUnitTest {
    private PatientPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserDisplayPanel browserDisplayPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PatientPanelSelectionChangedEvent(ALEX);

        guiRobot.interact(() -> browserDisplayPanel = new BrowserDisplayPanel());
        uiPartRule.setUiPart(browserDisplayPanel);

        browserPanelHandle = new BrowserPanelHandle(browserDisplayPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(
                BrowserDisplayPanel.SEARCH_PAGE_URL + ALEX.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
