package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.BaseEvent;

//@@author chivent
/**
 * An event that notifies StatusBarFooter on a change of directory.
 */
public class ChangeDirectoryEvent extends BaseEvent {

    public final String directory;

    /**
     * Constructor for ChangeDirectoryEvent
     *
     * @param directory The current directory user is in
     */
    public ChangeDirectoryEvent(String directory) {
        this.directory = requireNonNull(directory);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
