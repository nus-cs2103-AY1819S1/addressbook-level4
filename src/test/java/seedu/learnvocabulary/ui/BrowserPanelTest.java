package seedu.learnvocabulary.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.learnvocabulary.testutil.EventsUtil.postNow;
import static seedu.learnvocabulary.testutil.TypicalWords.ALICE;
import static seedu.learnvocabulary.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.learnvocabulary.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.learnvocabulary.MainApp;
import seedu.learnvocabulary.commons.events.ui.WordPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private WordPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new WordPanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a word
        postNow(selectionChangedEventStub);
        URL expectedWordUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedWordUrl, browserPanelHandle.getLoadedUrl());
    }
}
