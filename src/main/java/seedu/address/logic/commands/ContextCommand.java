package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a pre-filled ResumeEntry.
 */
public class ContextCommand extends Command {

    public static final String COMMAND_WORD = "nus";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a pre-filled resume entry."
                                               + "Parameters: EXPRESSION [MORE EXPRESSIONS]...\n"
                                               + "Expressions can be slang, partial phrases or full phrases.";

    public static final String MESSAGE_SUCCESS = "Created a resume entry for %1s.";

    public static final String MESSAGE_NO_RESUME_ENTRY = "There is no pre-filled resume entry: %1s.";

    public static final String MESSAGE_SUGGESTION = "Please update the XML data so that your slang is recognised,"
                                                    + "or provide a more specific search expression.";

    /**
     * A combination of slang, partial phrases or full phrases entered by the user.
     * Example of an expression: "compsci", "comp sci", "ug" and "undegrad"
     */
    private final String expression;

    public ContextCommand(String expression) {

        requireNonNull(expression);
        this.expression = expression;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String possibleEventName = model.getPossibleEventName(expression);

        Command addEntryCommand = model.getContextualResumeEntry(possibleEventName)
                                       .map(AddEntryCommand::new)
                                       .orElseThrow(() -> new CommandException(String.format(MESSAGE_NO_RESUME_ENTRY,
                                               possibleEventName)));

        return addEntryCommand.execute(model, history);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
               || (other instanceof ContextCommand // instanceof handles nulls
                   && expression.equals(((ContextCommand) other).expression));
    }
}
