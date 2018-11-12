package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.ModelToDo.PREDICATE_SHOW_ALL_TODOLIST_EVENTS;
import static seedu.address.testutil.TypicalTodoListEvents.LECTURE;
import static seedu.address.testutil.TypicalTodoListEvents.TUTORIAL;

import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ToDoListBuilder;

public class ModelManagerToDoTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManagerToDo modelManagerToDo = new ModelManagerToDo();

    @Test
    public void hasToDo_nullToDo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManagerToDo.hasToDoListEvent(null);
    }

    @Test
    public void hasToDo_todoNotInToDoList_returnsFalse() {
        assertFalse(modelManagerToDo.hasToDoListEvent(LECTURE));
    }

    @Test
    public void hasToDo_toDoInToDoList_returnsTrue() {
        modelManagerToDo.addToDoListEvent(LECTURE);
        assertTrue(modelManagerToDo.hasToDoListEvent(LECTURE));
    }

    @Test
    public void getFilteredToDoList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManagerToDo.getFilteredToDoListEventList().remove(0);
    }

    @Test
    public void equals() {
        ToDoList toDoList = new ToDoListBuilder().withEvent(LECTURE).withEvent(TUTORIAL).build();
        ToDoList differentToDoList = new ToDoList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManagerToDo = new ModelManagerToDo(toDoList, userPrefs);
        ModelManagerToDo modelManagerToDoCopy = new ModelManagerToDo(toDoList, userPrefs);
        assertTrue(modelManagerToDo.equals(modelManagerToDoCopy));

        // same object -> returns true
        assertTrue(modelManagerToDo.equals(modelManagerToDo));

        // null -> returns false
        assertFalse(modelManagerToDo.equals(null));

        // different types -> returns false
        assertFalse(modelManagerToDo.equals(5));

        // different scheduler -> returns false
        assertFalse(modelManagerToDo.equals(new ModelManagerToDo(differentToDoList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManagerToDo.updateFilteredToDoListEventList(PREDICATE_SHOW_ALL_TODOLIST_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSchedulerFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManagerToDo.equals(new ModelManagerToDo(toDoList, differentUserPrefs)));
    }

}
