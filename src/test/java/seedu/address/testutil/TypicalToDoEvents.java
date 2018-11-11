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

    public static final CalendarEvent LECTURE = new CalendarEventBuilder().withTitle("CS2103 Lecture")
        .withStart("2018-11-15 16:00").withEnd("2018-11-15 18:00")
        .withVenue("i3 Auditorium")
        .withDescription("Abstraction, Gradle, JUnit")
        .withTags("lecture", "CS2103").build();
    public static final CalendarEvent BENSON = new CalendarEventBuilder().withTitle("CS2104 Tutorial")
        .withStart("2018-11-13 14:00").withEnd("2018-11-13 16:00")
        .withVenue("AS6 04-21")
        .withDescription("Monadic Parsers")
        .withTags("tutorial", "CS2104").build();
    public static final CalendarEvent CARL = new CalendarEventBuilder().withTitle("CS2040 Lab")
        .withStart("2018-11-14 08:00")
        .withEnd("2018-11-14 10:00")
        .withDescription("Linked Lists")
        .withVenue("COM1 02-09").build();
    public static final CalendarEvent DANIEL = new CalendarEventBuilder().withTitle("FIN3101 Seminar")
        .withStart("2018-11-16 10:00")
        .withEnd("2018-11-16 15:00")
        .withDescription("One-Fund Theorem")
        .withVenue("Marina Boulevard")
        .withTags("seminar", "FIN3101").build();
    public static final CalendarEvent ELLE = new CalendarEventBuilder().withTitle("Choir Practice")
        .withStart("2018-11-16 19:00")
        .withEnd("2018-11-16 22:00")
        .withDescription("Bring songbook")
        .withVenue("Little Tokyo").build();
    public static final CalendarEvent FIONA = new CalendarEventBuilder().withTitle("Career Fair")
        .withStart("2018-11-17 09:00")
        .withEnd("2018-11-17 18:00")
        .withDescription("Bring resume")
        .withVenue("MPSH 1").build();
    public static final CalendarEvent GEORGE = new CalendarEventBuilder().withTitle("Google Interview")
        .withStart("2018-11-14 14:00")
        .withEnd("2018-11-14 16:00")
        .withDescription("Bring water bottle")
        .withVenue("Mountain View").build();

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
