package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Task taskWithNoTags = new TaskBuilder().withTags(new String[0]).build();
        TaskCard taskCard = new TaskCard(taskWithNoTags, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, taskWithNoTags, 1);

        // with tags
        Task taskWithTags = new TaskBuilder().build();
        taskCard = new TaskCard(taskWithTags, 2);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, taskWithTags, 2);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard personCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different task, same index -> returns false
        Task differentTask = new TaskBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(personCard.equals(new TaskCard(task, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Task expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify task details are displayed correctly
        assertCardDisplaysPerson(expectedTask, personCardHandle);
    }
}
