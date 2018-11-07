package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;

import org.junit.Test;

import guitests.guihandles.ToDoListEventCardHandle;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.ToDoListEventBuilder;

public class ToDoListEventCardTest extends GuiUnitTest {

    // @Test
    /**
     * TODO pass test (and remove this placeholder javadoc comment which only exists to satisfy checkstyle)
     *
     */
    public void display() {
        ToDoListEvent toDoListEvent = new ToDoListEventBuilder().build();
        ToDoListEventCard toDoListEventCard = new ToDoListEventCard(toDoListEvent, 2);
        uiPartRule.setUiPart(toDoListEventCard);
        assertCardDisplay(toDoListEventCard, toDoListEvent, 2);
    }

    @Test
    public void equals() {
        ToDoListEvent toDoListEvent = new ToDoListEventBuilder().build();
        ToDoListEventCard toDoListEventCard = new ToDoListEventCard(toDoListEvent, 0);

        // same todolist event, same index -> returns true
        ToDoListEventCard copy = new ToDoListEventCard(toDoListEvent, 0);
        assertTrue(toDoListEventCard.equals(copy));

        // same object -> returns true
        assertTrue(toDoListEventCard.equals(toDoListEventCard));

        // null -> returns false
        assertFalse(toDoListEventCard.equals(null));

        // different types -> returns false
        assertFalse(toDoListEventCard.equals(0));

        // different todolist event, same index -> returns false
        ToDoListEvent differentToDoListEvent = new ToDoListEventBuilder().withTitle("differentName").build();
        assertFalse(toDoListEventCard.equals(new ToDoListEventCard(differentToDoListEvent, 0)));

        // same todolist event, different index -> returns false
        assertFalse(toDoListEventCard.equals(new ToDoListEventCard(toDoListEvent, 1)));
    }

    /**
     * Asserts that {@code toDoListEventCard} displays the details of {@code expectedToDoListEvent} correctly and
     * matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ToDoListEventCard toDoListEventCard, ToDoListEvent expectedToDoListEvent,
                                   int expectedId) {
        guiRobot.pauseForHuman();

        ToDoListEventCardHandle toDoListEventCardHandle = new ToDoListEventCardHandle(toDoListEventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", toDoListEventCardHandle.getId());

        // verify calendarevent details are displayed correctly
        assertCardDisplaysToDo(expectedToDoListEvent, toDoListEventCardHandle);
    }

}
