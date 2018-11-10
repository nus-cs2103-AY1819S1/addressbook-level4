package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.A_TASK;

import guitests.guihandles.TaskViewPanelHandle;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.LogicManagerTest;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class TaskViewPanelTest extends GuiUnitTest {
    private TaskPanelSelectionChangedEvent selectionChangedEventStub;

    private TaskViewPanel taskViewPanel;
    private TaskViewPanelHandle taskViewPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new TaskPanelSelectionChangedEvent(A_TASK);

        Model model = new ModelManager();
        model.addTask(A_TASK);
        Logic logic = new LogicManager(model);
        guiRobot.interact(() -> taskViewPanel = new TaskViewPanel(logic));
        uiPartRule.setUiPart(taskViewPanel);

        taskViewPanelHandle = new TaskViewPanelHandle(taskViewPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
    }
}
