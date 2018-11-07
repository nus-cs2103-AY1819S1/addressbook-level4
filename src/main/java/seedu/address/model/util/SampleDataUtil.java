package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.Scheduler;
import seedu.address.model.ToDoList;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.DateTimeInfo;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Contains utility methods for populating {@code Scheduler} with sample data.
 * Contains utility methods for populating {@code ToDoList} with sample data.
 */
public class SampleDataUtil {
    public static CalendarEvent[] getSampleCalendarEvents() {
        return new CalendarEvent[]{
            new CalendarEvent(new Title("Alex Yeoh"), new Description("87438807"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"),
                    new DateTime("2018-10-16 16:00")),
                new Venue("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new CalendarEvent(new Title("Bernice Yu"), new Description("99272758"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"),
                    new DateTime("2018-10-16 16:00")),
                new Venue("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new CalendarEvent(new Title("Charlotte Oliveiro"), new Description("93210283"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"),
                    new DateTime("2018-10-16 16:00")),
                new Venue("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new CalendarEvent(new Title("David Li"), new Description("91031282"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"),
                    new DateTime("2018-10-16 16:00")),
                new Venue("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new CalendarEvent(new Title("Irfan Ibrahim"), new Description("92492021"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"),
                    new DateTime("2018-10-16 16:00")),
                new Venue("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new CalendarEvent(new Title("Roy Balakrishnan"), new Description("92624417"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"),
                    new DateTime("2018-10-16 16:00")),
                new Venue("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ToDoListEvent[] getSampleToDoListEvents() {
        return new ToDoListEvent[]{
            new ToDoListEvent(new Title("CS3230 Homework"), new Description("divide and conquer"),
                new Priority("M")),
            new ToDoListEvent(new Title("CS3241 Lab4"), new Description("draw curve"),
                new Priority("H")),
            new ToDoListEvent(new Title("JS1011 Homework"), new Description("watch Akira movie"),
                new Priority("L")),
            new ToDoListEvent(new Title("CS2103 Project"), new Description("finish UI design"),
                new Priority("H")),
            new ToDoListEvent(new Title("CS2106 Lab5"), new Description("final lab"),
                new Priority("M")),
            new ToDoListEvent(new Title("Modify resume"), new Description("activities"),
                new Priority("L"))
        };
    }

    public static ReadOnlyScheduler getSampleScheduler() {
        Scheduler sampleScheduler = new Scheduler();
        for (CalendarEvent sampleCalendarEvent : getSampleCalendarEvents()) {
            sampleScheduler.addCalendarEvent(sampleCalendarEvent);
        }
        return sampleScheduler;
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        ToDoList sampleToDoList = new ToDoList();
        for (ToDoListEvent sampleToDoListEvent : getSampleToDoListEvents()) {
            sampleToDoList.addToDoListEvent(sampleToDoListEvent);
        }
        return sampleToDoList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
