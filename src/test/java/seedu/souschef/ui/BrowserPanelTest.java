package seedu.souschef.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.souschef.testutil.EventsUtil.postNow;
import static seedu.souschef.testutil.TypicalRecipes.APPLE;
import static seedu.souschef.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.souschef.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.souschef.MainApp;
import seedu.souschef.commons.events.ui.RecipePanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private RecipePanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new RecipePanelSelectionChangedEvent(APPLE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a recipe
        postNow(selectionChangedEventStub);
        URL expectedRecipeUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + APPLE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecipeUrl, browserPanelHandle.getLoadedUrl());
    }
}
