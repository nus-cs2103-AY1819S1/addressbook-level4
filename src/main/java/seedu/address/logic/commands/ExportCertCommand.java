package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports a PDF document with data on a volunteer's involvement with the organisation.
 */
public class ExportCertCommand extends Command {

    public static final String COMMAND_WORD = "exportcert";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a PDF certificate for the volunteer at "
            + "the specified index in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * @param index of the person in the filtered person list whose certificate is to be generated and exported
     */
    public ExportCertCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        // Case: Both same object
        if (other == this) {
            return true;
        }

        // Case: Handle null, not instance of
        if (!(other instanceof ExportCertCommand)) {
            return false;
        }

        // Compare internal fields
        ExportCertCommand e = (ExportCertCommand) other;
        return index.equals(e.index);
    }
}
