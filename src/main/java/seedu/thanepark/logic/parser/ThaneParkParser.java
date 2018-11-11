package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.thanepark.logic.commands.AddCommand;
import seedu.thanepark.logic.commands.ClearCommand;
import seedu.thanepark.logic.commands.Command;
import seedu.thanepark.logic.commands.DeleteCommand;
import seedu.thanepark.logic.commands.ExitCommand;
import seedu.thanepark.logic.commands.FilterCommand;
import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.logic.commands.HelpCommand;
import seedu.thanepark.logic.commands.HistoryCommand;
import seedu.thanepark.logic.commands.MaintainCommand;
import seedu.thanepark.logic.commands.OpenCommand;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.ShutDownCommand;
import seedu.thanepark.logic.commands.SuggestCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.logic.commands.UpdateCommand;
import seedu.thanepark.logic.commands.ViewAllCommand;
import seedu.thanepark.logic.commands.ViewCommand;
import seedu.thanepark.logic.commands.ViewStatusCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class ThaneParkParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case UpdateCommand.COMMAND_WORD:
            return new UpdateCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommandParser().parse(arguments);

        case MaintainCommand.COMMAND_WORD:
            return new MaintainCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ShutDownCommand.COMMAND_WORD:
            return new ShutDownCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ViewAllCommand.COMMAND_WORD:
            return new ViewAllCommand();

        case ViewStatusCommand.COMMAND_WORD:
            return new ViewStatusCommandParser().parse(arguments);

        default:
            SuggestCommand command = new SuggestCommand(commandWord);
            if (!command.isPrefixValid()) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
            return command;
        }
    }

}
