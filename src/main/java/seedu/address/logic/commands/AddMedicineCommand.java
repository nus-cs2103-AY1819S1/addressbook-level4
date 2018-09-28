package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddMedicineCommand extends Command {
    public static final String COMMAND_WORD = "addMedicine";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
