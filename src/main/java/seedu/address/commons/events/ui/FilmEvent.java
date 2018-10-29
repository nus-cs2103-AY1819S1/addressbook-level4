package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.events.BaseEvent;

//@@author chivent
/**
 * An event that notifies StatusBarFooter regarding a login status change.
 */
public class FilmEvent extends BaseEvent {

    public final List<Path> paths;

    /**
     * Constructor for LoginStatusEvent
     *
     * @param paths List of paths to new images
     */
    public FilmEvent(List<Path> paths) {
        this.paths = requireNonNull(paths);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
