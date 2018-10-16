package seedu.address.testutil;

import seedu.address.model.todolist.TodoListEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code TodoListEvent} objects to be used in tests.
 */
public class TypicalTodoListEvents {
    public static final TodoListEvent MIDTERM = new TodoListEventBuilder().withTitle("CS2103 Midterm")
            .withDescription("cover all materials from week 1 to week 7")
            .withPriority("1").build();
    public static final TodoListEvent TUTORIAL = new TodoListEventBuilder().withTitle("CS2103 Tutorial")
            .withDescription("project mock demo")
            .withPriority("2").build();
    public static final TodoListEvent WORKSHOP = new TodoListEventBuilder().withTitle("Interview workshop")
            .withDescription("will be helpful for interview")
            .withPriority("3").build();
    public static final TodoListEvent ENTERTAINMENT = new TodoListEventBuilder().withTitle("Daniel's birthday party")
            .withDescription("remember to buy gifts")
            .withPriority("2").build();


    private TypicalTodoListEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code Scheduler} with all the typical persons.
     */
    /*public static Scheduler getTypicalScheduler() {
        Scheduler ab = new Scheduler();
        for (CalendarEvent calendarEvent : getTypicalCalendarEvents()) {
            ab.addCalendarEvent(calendarEvent);
        }
        return ab;
    }*/

    public static List<TodoListEvent> getTypicalTodoListEvents() {
        return new ArrayList<>(Arrays.asList(MIDTERM, TUTORIAL, WORKSHOP, ENTERTAINMENT));
    }
}
