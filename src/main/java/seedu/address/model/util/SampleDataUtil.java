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
            new Task(new Name("Attack food"), new DueDate("01-10-18"), new PriorityValue("1"),
                    new Description("Chop people into little pieces with sharp knife, then eat them"),
                    getLabelSet("friends")),
            new Task(new Name("Bounce ball"), new DueDate("02-10-18"), new PriorityValue("2"),
                    new Description("Forcefully propel spherical implement towards acquaintances"),
                    getLabelSet("colleagues", "friends")),
            new Task(new Name("Count eggs"), new DueDate("03-10-18"),
                    new PriorityValue("3"),
                    new Description("then eat them"),
                    getLabelSet("neighbours")),
            new Task(new Name("Dancing queen"), new DueDate("04-10-18"), new PriorityValue("4"),
                    new Description("Feel the beat from the tambourine"),
                    getLabelSet("family")),
            new Task(new Name("Induce happiness"), new DueDate("05-10-18"), new PriorityValue("5"),
                    new Description("Inject heroin"),
                    getLabelSet("classmates")),
            new Task(new Name("Run home"), new DueDate("06-10-18"), new PriorityValue("3"),
                    new Description("Just go"),
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
