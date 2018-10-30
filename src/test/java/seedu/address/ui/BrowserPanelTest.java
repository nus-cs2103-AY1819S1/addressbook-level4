package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_ONE;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_ONE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.OccasionPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedPersonEventStub;
    private ModulePanelSelectionChangedEvent selectionChangedModuleEventStub;
    private OccasionPanelSelectionChangedEvent selectionChangedOccasionEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedPersonEventStub = new PersonPanelSelectionChangedEvent(ALICE);
        selectionChangedModuleEventStub = new ModulePanelSelectionChangedEvent(TYPICAL_MODULE_ONE);
        selectionChangedOccasionEventStub = new OccasionPanelSelectionChangedEvent(TYPICAL_OCCASION_ONE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedPersonEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.SEARCH_PERSON_PAGE_URL
                + ALICE.getName().fullName.replaceAll(" ", "%20"));
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a module
        postNow(selectionChangedModuleEventStub);
        URL expectedModuleUrl = new URL(BrowserPanel.SEARCH_MODULE_PAGE_URL
                + TYPICAL_MODULE_ONE.getModuleCode().fullModuleCode.replaceAll(" ", "%20"));
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedModuleUrl, browserPanelHandle.getLoadedUrl());

        postNow(selectionChangedOccasionEventStub);
        URL expectedOccasionUrl = new URL(BrowserPanel.SEARCH_OCCASION_PAGE_URL
                + TYPICAL_OCCASION_ONE.getOccasionName().fullOccasionName.replaceAll(" ", "%20"));
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedOccasionUrl, browserPanelHandle.getLoadedUrl());
    }
}
