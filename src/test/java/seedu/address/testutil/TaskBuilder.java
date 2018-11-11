package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Label;
import seedu.address.model.task.Dependencies;
import seedu.address.model.task.Description;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Name;
import seedu.address.model.task.PriorityValue;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Address CS2103 email";
    public static final String DEFAULT_DUEDATE = "20-12-18";
    public static final String DEFAULT_PRIORITY_VALUE = "2"; // Setting to 1 clashes with A_TASK
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private DueDate dueDate;
    private PriorityValue priorityValue;
    private Description description;
    private Set<Label> labels;
    private Status status;
    private Dependencies dependencies;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        dueDate = new DueDate(DEFAULT_DUEDATE);
        priorityValue = new PriorityValue(DEFAULT_PRIORITY_VALUE);
        description = new Description(DEFAULT_DESCRIPTION);
        labels = new HashSet<>();
        status = Status.IN_PROGRESS;
        dependencies = new Dependencies();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        dueDate = taskToCopy.getDueDate();
        priorityValue = taskToCopy.getPriorityValue();
        description = taskToCopy.getDescription();
        labels = new HashSet<>(taskToCopy.getLabels());
        status = taskToCopy.getStatus();
        dependencies = new Dependencies(taskToCopy.getDependency().getHashes());
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code labels} into a {@code Set<Label>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withLabels(String... labels) {
        this.labels = SampleDataUtil.getLabelSet(labels);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withDueDate(String dueDate) {
        this.dueDate = new DueDate(dueDate);
        return this;
    }

    /**
     * Sets the {@code PriorityValue} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriorityValue(String priorityValue) {
        this.priorityValue = new PriorityValue(priorityValue);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code Dependecy} of the {@code Task} that we are building.
     */
    public TaskBuilder withDependency(Task task) {
        this.dependencies = this.dependencies.addDependency(task);
        return this;
    }

    public Task build() {
        return new Task(name, dueDate, priorityValue, description, labels, status, dependencies);
    }

}
