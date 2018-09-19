package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Remark;

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
    private final Remark remark;

    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(index + " " + remark);
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof RemarkCommand)) {
            return false;
        }

        if(((RemarkCommand) other).remark.equals(this.remark) &&
                ((RemarkCommand) other).index.equals(index)
        ) {
            return true;
        }

        return false;
    }
}
