package seedu.address.testutil;


import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * A utility class to help with building TodoList objects.
 */
public class TodoListBuilder {

    public static final String DEFAULT_TITLE = "CS2103 Lecture";
    public static final String DEFAULT_DESCRIPTION = "Abstraction, IntelliJ, Gradle";
    public static final String DEFAULT_PRIORITY = "1";

    private Title title;
    private Description description;
    private Venue venue;
    private Set<Tag> tags;
}
