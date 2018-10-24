package ssp.scheduleplanner.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static ssp.scheduleplanner.testutil.EventsUtil.postNow;
import static ssp.scheduleplanner.testutil.TypicalTasks.ALICE;
import static ssp.scheduleplanner.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SidebarPanelHandle;
import ssp.scheduleplanner.MainApp;
import ssp.scheduleplanner.commons.events.ui.TaskPanelSelectionChangedEvent;
import ssp.scheduleplanner.logic.Logic;
import ssp.scheduleplanner.logic.LogicManager;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;

public class SidebarPanelTest extends GuiUnitTest {
    private TaskPanelSelectionChangedEvent selectionChangedEventStub;

    private SidebarPanel sidebarPanel;
    private SidebarPanelHandle sidebarPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new TaskPanelSelectionChangedEvent(ALICE);
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);
        guiRobot.interact(() -> sidebarPanel = new SidebarPanel(logic));
        uiPartRule.setUiPart(sidebarPanel);

        sidebarPanelHandle = new SidebarPanelHandle(sidebarPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        //URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        //assertEquals(expectedDefaultPageUrl, sidebarPanelHandle.getLoadedUrl());

        // associated web page of a task
        postNow(selectionChangedEventStub);
        //URL expectedTaskUrl = new URL(SidebarPanel.SEARCH_PAGE_URL + ALICE.getName().fullName.replaceAll(" ", "%20"));

        //waitUntilBrowserLoaded(sidebarPanelHandle);
        //assertEquals(expectedTaskUrl, sidebarPanelHandle.getLoadedUrl());
    }
}
