package ssp.scheduleplanner.testutil;

import java.util.HashSet;
import java.util.Set;

import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.model.task.Name;
import ssp.scheduleplanner.model.task.Priority;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.model.task.Venue;
import ssp.scheduleplanner.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "111155";
    public static final String DEFAULT_PRIORITY = "1";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Date date;
    private Priority priority;
    private Venue venue;
    private Set<Tag> tags;

    public TaskBuilder() {
        name = new Name(
                DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        priority = new Priority(DEFAULT_PRIORITY);
        venue = new Venue(DEFAULT_VENUE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        date = taskToCopy.getDate();
        priority = taskToCopy.getPriority();
        venue = taskToCopy.getVenue();
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Task} that we are building.
     */
    public TaskBuilder withVenue(String address) {
        this.venue = new Venue(address);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public Task build() {
        return new Task(name, date, priority, venue, tags);
    }

}
