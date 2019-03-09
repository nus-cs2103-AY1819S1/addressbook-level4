package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

/**
 * Adds a playlist to the library.
 */
public class PlaylistNewCommand extends Command {

    public static final String COMMAND_PHRASE = "playlist new";

    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Creates a new empty playlist and saves it into the "
            + "library."
            + "Parameters: "
            + PREFIX_PLAYLIST + "PLAYLIST "
            + "[" + PREFIX_TRACK + "TRACK]...\n"
            + "Example: " + COMMAND_PHRASE + " "
            + PREFIX_PLAYLIST + "Favourites "
            + PREFIX_TRACK + "Ihojin no Yaiba "
            + PREFIX_TRACK + "acyort";

    public static final String MESSAGE_SUCCESS = "New playlist added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAYLIST = "This playlist already exists in the library";

    private final Playlist toAdd;

    /**
     * Creates an PlaylistNewCommand to add the specified {@code Playlist}
     */
    public PlaylistNewCommand(Playlist playlist) {
        requireNonNull(playlist);
        toAdd = playlist;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPlaylist(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAYLIST);
        }

        model.addPlaylist(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlaylistNewCommand // instanceof handles nulls
                && toAdd.equals(((PlaylistNewCommand) other).toAdd));
    }
}
