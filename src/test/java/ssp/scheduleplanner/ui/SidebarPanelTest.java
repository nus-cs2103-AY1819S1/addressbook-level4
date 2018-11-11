package ssp.scheduleplanner.ui;

import static org.junit.Assert.assertEquals;
import static ssp.scheduleplanner.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SidebarPanelHandle;
import ssp.scheduleplanner.commons.events.ui.ShowTagsRequestEvent;
import ssp.scheduleplanner.logic.Logic;
import ssp.scheduleplanner.logic.LogicManager;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;

public class SidebarPanelTest extends GuiUnitTest {

    private static final ShowTagsRequestEvent NEW_SHOW_TAGS_EVENT = new ShowTagsRequestEvent("Modules");

    private SidebarPanel sidebarPanel;
    private SidebarPanelHandle sidebarPanelHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);
        guiRobot.interact(() -> sidebarPanel = new SidebarPanel(logic));
        uiPartRule.setUiPart(sidebarPanel);

        sidebarPanelHandle = new SidebarPanelHandle(sidebarPanel.getRoot());
    }

    @Test
    public void display() {
        postNow(NEW_SHOW_TAGS_EVENT);
        String expectedPaneName = "Modules";
        assertEquals(expectedPaneName, sidebarPanelHandle.getExpandedPaneName());
    }
}
