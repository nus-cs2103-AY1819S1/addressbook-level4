package seedu.address.testutil;

import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.TodoListEvent;


/**
 * A utility class to help with building TodoListEvent objects.
 */
public class TodoListEventBuilder {

    public static final String DEFAULT_TITLE = "CS2103 Lecture";
    public static final String DEFAULT_DESCRIPTION = "Abstraction, IntelliJ, Gradle";
    public static final String DEFAULT_PRIORITY = "1";

    private Title title;
    private Description description;
    private Priority priority;

    public TodoListEventBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the TodoListEventBuilder with the data of {@code todoListEventToCopy}.
     */
    public TodoListEventBuilder(TodoListEvent todoListEventToCopy) {
        title = todoListEventToCopy.getTitle();
        description = todoListEventToCopy.getDescription();
        priority = todoListEventToCopy.getPriority();
    }

    /**
     * Sets the {@code Title} of the {@code TodoListEvent} that we are building.
     */
    public TodoListEventBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code TodoListEvent} that we are building.
     */
    public TodoListEventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code TodoListEvent} that we are building.
     */
    public TodoListEventBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public TodoListEvent build() {
        return new TodoListEvent(title, description, priority);
    }
}
