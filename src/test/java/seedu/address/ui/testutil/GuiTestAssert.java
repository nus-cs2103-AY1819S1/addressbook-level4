package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import seedu.address.model.task.Task;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TaskCardHandle expectedCard,
                                        TaskCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTask}.
     */
    public static void assertCardDisplaysPerson(Task expectedTask, TaskCardHandle actualCard) {
        assertEquals(expectedTask.getName().value, actualCard.getName());
        assertEquals(expectedTask.getPhone().value, actualCard.getPhone());
        assertEquals(expectedTask.getEmail().value, actualCard.getEmail());
        assertEquals(expectedTask.getAddress().value, actualCard.getAddress());
        assertEquals(expectedTask.getTags().stream().map(tag -> tag.tagName)
                .collect(Collectors.toList()),
            actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks}
     * correctly and in the correct order.
     */
    public static void assertListMatching(TaskListPanelHandle taskListPanelHandle,
                                          Task... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            taskListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(tasks[i], taskListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks}
     * correctly and in the correct order.
     */
    public static void assertListMatching(TaskListPanelHandle taskListPanelHandle,
                                          List<Task> tasks) {
        assertListMatching(taskListPanelHandle, tasks.toArray(new Task[0]));
    }

    /**
     * Asserts the size of the list in {@code taskListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(TaskListPanelHandle taskListPanelHandle, int size) {
        int numberOfPeople = taskListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle,
                                           String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
