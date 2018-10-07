package seedu.address.logic.commands;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

/**
 * edits remark of index of person in addressbook
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits remark of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_REMARK + "REMARK] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to drink coffee ";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
