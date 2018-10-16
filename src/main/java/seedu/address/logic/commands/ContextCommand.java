package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Adds a pre-filled ResumeEntry.
 */
public class ContextCommand extends Command {

    public static final String COMMAND_WORD = "nus";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pre-filled ResumeEntry to ResuMaker's data."
                                               + "Parameters: EXPRESSION [MORE EXPRESSIONS]...\n"
                                               + "Expressions can be slang, partial phrases or full phrases.";

    /* TODO: Utilise Java String placeholders in the MESSAGE_SUCCESS */
    public static final String MESSAGE_SUCCESS = "Created a resume entry for %1s.";

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
    public CommandResult execute(Model model, CommandHistory history) {
        String possibleEventName = model.getPossibleEventName(expression);
        return new CommandResult(String.format(MESSAGE_SUCCESS, possibleEventName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
               || (other instanceof ContextCommand // instanceof handles nulls
                   && expression.equals(((ContextCommand) other).expression));
    }
}
