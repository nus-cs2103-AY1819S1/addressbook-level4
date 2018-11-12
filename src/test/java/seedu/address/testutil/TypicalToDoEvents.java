package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ToDoList;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * A utility class containing a list of {@code ToDoEvent} objects to be used in tests.
 */
public class TypicalToDoEvents {

    public static final ToDoListEvent CS1000 = new ToDoListEventBuilder()
        .withTitle("CS1000")
        .withDescription("teach to friends")
        .withPriority("L").build();
    public static final ToDoListEvent CS2000 = new ToDoListEventBuilder()
        .withTitle("CS2000")
        .withDescription("teach to friends")
        .withPriority("M").build();
    public static final ToDoListEvent CS3000 = new ToDoListEventBuilder()
        .withTitle("CS3000")
        .withDescription("teach to friends")
        .withPriority("H").build();
    public static final ToDoListEvent CS4000 = new ToDoListEventBuilder()
        .withTitle("CS4000")
        .withDescription("seek help from prof")
        .withPriority("L").build();
    public static final ToDoListEvent CS5000 = new ToDoListEventBuilder()
        .withTitle("CS5000")
        .withDescription("seek help from prof")
        .withPriority("M").build();
    public static final ToDoListEvent CS6000 = new ToDoListEventBuilder()
        .withTitle("CS6000")
        .withDescription("seek help from prof")
        .withPriority("H").build();
    public static final ToDoListEvent CS7000 = new ToDoListEventBuilder()
        .withTitle("CS7000")
        .withDescription("extremely difficult")
        .withPriority("H").build();
    public static final ToDoListEvent CS8000 = new ToDoListEventBuilder()
        .withTitle("CS8000")
        .withDescription("extremely difficult")
        .withPriority("H").build();

    private TypicalToDoEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code ToDoList} with all the typical persons.
     */
    public static ToDoList getTypicalToDoList() {
        ToDoList toDoList = new ToDoList();
        for (ToDoListEvent todo : getTypicalToDoListEvents()) {
            toDoList.addToDoListEvent(todo);
        }
        return toDoList;
    }

    public static List<ToDoListEvent> getTypicalToDoListEvents() {
        return new ArrayList<>(Arrays.asList(CS1000, CS2000, CS3000, CS4000, CS5000, CS6000));
    }
}
