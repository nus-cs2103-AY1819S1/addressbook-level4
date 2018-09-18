package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RemarkCommand extends Command {
    public static final String COMMAND_WORD = "remark";
    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }
}
