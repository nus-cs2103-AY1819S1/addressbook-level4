package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

/**
 * Adds a playlist to the jxmusic book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a playlist to the jxmusic book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New playlist added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAYLIST = "This playlist already exists in the jxmusic book";

    private final Playlist toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Playlist}
     */
    public AddCommand(Playlist playlist) {
        requireNonNull(playlist);
        toAdd = playlist;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPlaylist(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAYLIST);
        }

        model.addPlaylist(toAdd);
        // model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
