package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a picture to a contact in the address book.
 */
public class PictureCommand extends Command {

    public static final String COMMAND_WORD = "pic";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a picture to a contact in the address book "
        + "by the name used in the displayed person list.\n"
        + "Parameters: " + PREFIX_NAME + "NAME "
        + "[" + PREFIX_FILE_LOCATION + "FILE_LOCATION]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "johndoe "
        + PREFIX_FILE_LOCATION + "/images/johndoe.jpg";

    public static final String MESSAGE_PICTURE_SUCCESS = "Added picture for Person: %1$s";
    public static final String MESSAGE_INVALID_PICTURE = "The image file could not be found.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INVALID_PICTURE);
    }
}
