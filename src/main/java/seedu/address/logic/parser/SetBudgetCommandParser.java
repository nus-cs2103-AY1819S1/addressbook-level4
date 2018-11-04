package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetBudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budget.TotalBudget;


//@author winsonhys
/**
 * Parses input arguments and creates a new SetBudgetCommand object
 */
public class SetBudgetCommandParser implements Parser<SetBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetBudgetCommand parse(String userInput) throws ParseException {
        try {
            TotalBudget totalBudget = new TotalBudget(userInput.trim());
            if (totalBudget.getBudgetCap() <= 0) {
                throw new NegativeException();
            }
            return new SetBudgetCommand(totalBudget);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetBudgetCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Exception that is thrown when user enters a negative value.
     */
    private static class NegativeException extends ParseException {
        public NegativeException() {
            super("Only values more than 0 are allowed");
        }
    }
}
