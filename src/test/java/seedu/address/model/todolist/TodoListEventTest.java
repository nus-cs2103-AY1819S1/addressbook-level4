package seedu.address.model.todolist;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.testutil.TypicalTodoListEvents.MIDTERM;
import static seedu.address.testutil.TypicalTodoListEvents.TUTORIAL;

import seedu.address.testutil.TodoListEventBuilder;

public class TodoListEventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameTodoListEvent() {
        // same object -> returns true
        assertTrue(MIDTERM.isSameTodoListEvent(MIDTERM));

        // null -> returns false
        assertFalse(MIDTERM.isSameTodoListEvent(null));

        // different description -> returns false
        TodoListEvent editedMidterm =
                new TodoListEventBuilder(MIDTERM).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(MIDTERM.isSameTodoListEvent(editedMidterm));

        // different title -> returns false
        editedMidterm = new TodoListEventBuilder(MIDTERM).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(MIDTERM.isSameTodoListEvent(editedMidterm));

        // same title, different description, different priority -> returns false
        editedMidterm =
                new TodoListEventBuilder(MIDTERM)
                        .withDescription(VALID_DESCRIPTION_TUTORIAL)
                        .withPriority(VALID_PRIORITY_TUTORIAL)
                        .build();
        assertFalse(MIDTERM.isSameTodoListEvent(editedMidterm));

        // same title, same description, different priority -> returns true
        editedMidterm =
                new TodoListEventBuilder(MIDTERM).withPriority(VALID_PRIORITY_TUTORIAL).build();
        assertTrue(MIDTERM.isSameTodoListEvent(editedMidterm));
    }

    @Test
    public void equals() {
        // same values -> returns true
        TodoListEvent midtermCopy = new TodoListEventBuilder(MIDTERM).build();
        assertTrue(MIDTERM.equals(midtermCopy));

        // same object -> returns true
        assertTrue(MIDTERM.equals(MIDTERM));

        // null -> returns false
        assertFalse(MIDTERM.equals(null));

        // different type -> returns false
        assertFalse(MIDTERM.equals(5));

        // different todoListEvents -> returns false
        assertFalse(MIDTERM.equals(TUTORIAL));

        // different title -> returns false
        TodoListEvent editedMidterm = new TodoListEventBuilder(MIDTERM).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(MIDTERM.equals(editedMidterm));

        // different description -> returns false
        editedMidterm = new TodoListEventBuilder(MIDTERM).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(MIDTERM.equals(editedMidterm));

        // different priority -> returns false
        editedMidterm = new TodoListEventBuilder(MIDTERM).withPriority(VALID_PRIORITY_TUTORIAL).build();
        assertFalse(MIDTERM.equals(editedMidterm));

    }
}