package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

public class RemarkCommand extends Command {
    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMARK + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "random remark";

    private final Index index;
    private final String remarkString;

    public RemarkCommand(Index index, String remarkString) {
        requireNonNull(index);
        requireNonNull(remarkString);

        this.index = index;
        this.remarkString = remarkString;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(index + " " + remarkString);
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof RemarkCommand)) {
            return false;
        }

        if(((RemarkCommand) other).remarkString.equals(this.remarkString) &&
                ((RemarkCommand) other).index.equals(index)
        ) {
            return true;
        }

        return false;
    }
}
