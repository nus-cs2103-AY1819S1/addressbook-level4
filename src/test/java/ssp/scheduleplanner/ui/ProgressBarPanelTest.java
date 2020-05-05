package ssp.scheduleplanner.ui;

import static java.lang.Double.NaN;
import static org.junit.Assert.assertEquals;
import static ssp.scheduleplanner.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ProgressBarPanelHandle;
import ssp.scheduleplanner.commons.events.model.SchedulePlannerChangedEvent;
import ssp.scheduleplanner.logic.Logic;
import ssp.scheduleplanner.logic.LogicManager;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.SchedulePlanner;

public class ProgressBarPanelTest extends GuiUnitTest {

    private ProgressBarPanel progressBarPanel;
    private ProgressBarPanelHandle progressBarPanelHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);
        guiRobot.interact(() -> progressBarPanel = new ProgressBarPanel(logic));
        uiPartRule.setUiPart(progressBarPanel);

        progressBarPanelHandle = new ProgressBarPanelHandle(progressBarPanel.getRoot());
    }

    @Test
    public void progress() {

        postNow(new SchedulePlannerChangedEvent(new SchedulePlanner()));
        Double expectedToday = NaN;
        Double expectedWeek = NaN;
        assertEquals(expectedToday, progressBarPanelHandle.getTodayProgress());
        assertEquals(expectedWeek, progressBarPanelHandle.getWeekProgress());
    }
}
