package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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

    public static final String MESSAGE_ARGUMENTS = "Drug name: %1$s, Amount Per Dose: %2$d, Times Per Day: %3$d";

    private final String drug;
    private final int amount;
    private final int count;

    /**
     *
     * @param drug prescribed for that appointment
     * @param amount per dose
     * @param count per day
     */

    public AddPrescriptionCommand(String drug, int amount, int count) {
        requireAllNonNull(drug, amount, count);

        this.drug = drug;
        this.amount = amount;
        this.count = count;

    }

    @Override
    public CommandResult execute(Model model,CommandHistory history) throws CommandException {

        throw new CommandException(String.format(MESSAGE_ARGUMENTS, drug, amount,count));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof AddPrescriptionCommand)) {
            return false;
        }

        AddPrescriptionCommand e = (AddPrescriptionCommand) o;
        return drug.equals(e.drug)
                && amount == e.amount
                && count == e.amount;

    }

}
