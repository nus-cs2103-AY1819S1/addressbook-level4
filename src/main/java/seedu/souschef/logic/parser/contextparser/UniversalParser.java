package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Pattern;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.ContextCommand;
import seedu.souschef.logic.commands.ExitCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.HistoryCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class UniversalParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     *
     * @param history
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(CommandHistory history, String userInput) throws ParseException {
        final String commandWord = userInput.substring(1);
        switch (commandWord) {
        case "recipe":
            return new ContextCommand("Recipe");

        case "ingredientmanager":
            return new ContextCommand("Ingredient Manager");

        case "healthplan":
            return new ContextCommand("Health Plan");

        case "mealplanner":
            return new ContextCommand("Meal Planner");

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
