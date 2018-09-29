package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SAVING;

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Channels saving amount to wish."
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SAVING + "SAVING_AMOUNT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SAVING + "108.50";
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_USAGE);
    }
}
