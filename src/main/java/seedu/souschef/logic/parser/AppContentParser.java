package seedu.souschef.logic.parser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.logic.commands.ClearCommand;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.commands.EditCommand;
import seedu.souschef.logic.commands.ExitCommand;
import seedu.souschef.logic.commands.FindCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.HistoryCommand;
import seedu.souschef.logic.commands.ListCommand;
import seedu.souschef.logic.commands.RedoCommand;
import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.commands.SurpriseCommand;
import seedu.souschef.logic.commands.UndoCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;

/**
 * Parses user input.
 */
public class AppContentParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param model
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(Model model, String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parseRecipe(model, arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parseRecipe(model, arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parseRecipe(model, arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parseRecipe(model, arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parseRecipe(model, arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case SurpriseCommand.COMMAND_WORD:
            return new SurpriseCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
