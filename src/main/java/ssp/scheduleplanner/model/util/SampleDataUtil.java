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
            new Task(new Name("Homework"), new Date("111118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2100")),
            new Task(new Name("Assignment"), new Date("121118"), new Priority("3"),
                new Venue("Home"),
                getTagSet("CS2101"))
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
