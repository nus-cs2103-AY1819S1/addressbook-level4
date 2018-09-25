package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.person.Description;
import seedu.address.model.person.DueDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.PriorityValue;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Label;

/**
 * Contains utility methods for populating {@code TaskManager} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Name("Alex Yeoh"), new DueDate("87438807"), new PriorityValue("alexyeoh@example.com"),
                    new Description("Blk 30 Geylang Street 29, #06-40"),
                    getLabelSet("friends")),
            new Task(new Name("Bernice Yu"), new DueDate("99272758"), new PriorityValue("berniceyu@example.com"),
                    new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getLabelSet("colleagues", "friends")),
            new Task(new Name("Charlotte Oliveiro"), new DueDate("93210283"),
                    new PriorityValue("charlotte@example.com"),
                    new Description("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getLabelSet("neighbours")),
            new Task(new Name("David Li"), new DueDate("91031282"), new PriorityValue("lidavid@example.com"),
                    new Description("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getLabelSet("family")),
            new Task(new Name("Irfan Ibrahim"), new DueDate("92492021"), new PriorityValue("irfan@example.com"),
                    new Description("Blk 47 Tampines Street 20, #17-35"),
                    getLabelSet("classmates")),
            new Task(new Name("Roy Balakrishnan"), new DueDate("92624417"), new PriorityValue("royb@example.com"),
                    new Description("Blk 45 Aljunied Street 85, #11-31"),
                    getLabelSet("colleagues"))
        };
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        TaskManager sampleAb = new TaskManager();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

    /**
     * Returns a label set containing the list of strings given.
     */
    public static Set<Label> getLabelSet(String... strings) {
        return Arrays.stream(strings)
                .map(Label::new)
                .collect(Collectors.toSet());
    }

}
