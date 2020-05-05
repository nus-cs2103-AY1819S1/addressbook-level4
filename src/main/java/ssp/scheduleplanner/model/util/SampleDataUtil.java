package ssp.scheduleplanner.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import ssp.scheduleplanner.model.ReadOnlySchedulePlanner;
import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.model.task.Name;
import ssp.scheduleplanner.model.task.Priority;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.model.task.Venue;

/**
 * Contains utility methods for populating {@code SchedulePlanner} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("SAMPLE Do 2100 Tutorial"), new Date("011118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2100", "Sample")),
            new Task(new Name("SAMPLE Do 2101 Assignment"), new Date("011118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2101", "Sample")),
            new Task(new Name("SAMPLE 2103 Tutorial Prep"), new Date("061118"), new Priority("2"),
                new Venue("Home"),
                getTagSet("CS2103", "Tutorial", "Sample")),
            new Task(new Name("SAMPLE 2100 Tutorial Prep"), new Date("051118"), new Priority("2"),
                new Venue("COM1"),
                getTagSet("CS2100", "Tutorial", "Sample")),
            new Task(new Name("SAMPLE 2103 PE Dry Run"), new Date("021118"), new Priority("3"),
                new Venue("I3"),
                getTagSet("CS2103", "Sample")),
            new Task(new Name("SAMPLE Groceries for Mum"), new Date("311018"), new Priority("1"),
                new Venue("Clementi Fairprice"),
                getTagSet("Errands", "Sample")),
            new Task(new Name("SAMPLE TGIF"), new Date("021118"), new Priority("3"),
                new Venue("Zouk"),
                getTagSet("Party", "Sample")),
            new Task(new Name("SAMPLE 2100 Lecture Prep"), new Date("031118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2100", "Sample")),
            new Task(new Name("SAMPLE 2106 Tutorial Prep"), new Date("041118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2106", "Sample")),
            new Task(new Name("SAMPLE 2103 Tutorial Prep"), new Date("081118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2103", "Sample")),
            new Task(new Name("SAMPLE 2334 Quiz Prep"), new Date("111118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("ST2334", "Sample")),
            new Task(new Name("SAMPLE 2103 Demo Prep"), new Date("151118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2103", "Sample")),
            new Task(new Name("SAMPLE 2103 PE Prep"), new Date("161118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2103", "Sample")),
            new Task(new Name("SAMPLE 2100 Tutorial Prep"), new Date("171118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2100", "Sample")),
            new Task(new Name("SAMPLE 1231 Tutorial Prep"), new Date("181118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS1231", "Sample")),
            new Task(new Name("SAMPLE Watch 2103 Lecture"), new Date("291118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2100", "Sample"))
        };
    }

    public static ReadOnlySchedulePlanner getSampleSchedulePlanner() {
        SchedulePlanner sampleAb = new SchedulePlanner();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
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
