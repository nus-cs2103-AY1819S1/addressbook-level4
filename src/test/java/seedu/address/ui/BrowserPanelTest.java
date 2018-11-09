package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalTasks.A_TASK;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private TaskPanelSelectionChangedEvent selectionChangedEventStub;

    private TaskViewPanel taskViewPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new TaskPanelSelectionChangedEvent(A_TASK);

        guiRobot.interact(() -> taskViewPanel = new TaskViewPanel());
        uiPartRule.setUiPart(taskViewPanel);

        browserPanelHandle = new BrowserPanelHandle(taskViewPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a task
        postNow(selectionChangedEventStub);
        URL expectedTaskUrl = new URL(TaskViewPanel.SEARCH_PAGE_URL + A_TASK.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedTaskUrl, browserPanelHandle.getLoadedUrl());
    }
}
