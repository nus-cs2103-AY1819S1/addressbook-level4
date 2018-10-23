package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG;

/**
 * Adds a prescription to an appointment
 */

public class AddPrescriptionCommand extends Command{

    public static final String COMMAND_WORD = "add-prescription";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a prescription to an appointment. "
            + "Parameters: "
            + PREFIX_DRUG + "DRUG_NAME "
            + PREFIX_AMOUNT + "AMOUNT_PER_DOSE "
            + PREFIX_COUNT + "TIMES_PER_DAY ";

    public static final String MESSAGE_NOT_YET_IMPLEMENTED = "Add prescription command not yet implemented";

    @Override
    public CommandResult execute(Model model,CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_YET_IMPLEMENTED);
    }

}
