package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Description;
import seedu.address.model.person.DueDate;
import seedu.address.model.person.PriorityValue;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Label;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DUEDATE = "85355255";
    public static final String DEFAULT_PRIORITYVALUE = "alice@gmail.com";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private DueDate dueDate;
    private PriorityValue priorityValue;
    private Description address;
    private Set<Label> labels;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        dueDate = new DueDate(DEFAULT_DUEDATE);
        priorityValue = new PriorityValue(DEFAULT_PRIORITYVALUE);
        address = new Description(DEFAULT_DESCRIPTION);
        labels = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(Task personToCopy) {
        name = personToCopy.getName();
        dueDate = personToCopy.getDueDate();
        priorityValue = personToCopy.getPriorityValue();
        address = personToCopy.getDescription();
        labels = new HashSet<>(personToCopy.getLabels());
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
    public TaskBuilder withLabels(String ... labels) {
        this.labels = SampleDataUtil.getLabelSet(labels);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.address = new Description(description);
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

    public Task build() {
        return new Task(name, dueDate, priorityValue, address, labels);
    }

}
