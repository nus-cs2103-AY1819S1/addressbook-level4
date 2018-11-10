package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.A_TASK;

import guitests.guihandles.TaskViewPanelHandle;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;

public class TaskViewPanelTest extends GuiUnitTest {
    private TaskPanelSelectionChangedEvent selectionChangedEventStub;

    private TaskViewPanel taskViewPanel;
    private TaskViewPanelHandle taskViewPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new TaskPanelSelectionChangedEvent(A_TASK);

        guiRobot.interact(() -> taskViewPanel = new TaskViewPanel(A_TASK));
        uiPartRule.setUiPart(taskViewPanel);

        taskViewPanelHandle = new TaskViewPanelHandle(taskViewPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
    }
}
