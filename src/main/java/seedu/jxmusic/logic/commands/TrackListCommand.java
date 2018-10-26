package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableSet;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.Track;

/**
 * Lists all the tracks inside the library to the user.
 */
public class TrackListCommand extends Command {
    public static final String COMMAND_PHRASE = "track list";
    public static final String MESSAGE_SUCCESS = "Listed all tracks";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyLibrary library = model.getLibrary();
        ObservableSet<Track> tracks = library.getTracks();
        String tracklist = "List of tracks: ";
        for (Track track : tracks) {
            tracklist = tracklist + track.getFileName() + ", ";
        }
        return new CommandResult(tracklist);
    }
}
