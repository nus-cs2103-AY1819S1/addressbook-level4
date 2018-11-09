package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.CdCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.commands.CreateConvertCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.LsCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.PrevCommand;
import seedu.address.logic.commands.RedoAllCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.UndoAllCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.canvas.CanvasCommand;
import seedu.address.logic.commands.google.GoogleCommand;
import seedu.address.logic.commands.layer.LayerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PiconsoParser {

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
        case CdCommand.COMMAND_WORD:
            return new CdCommandParser().parse(arguments);

        case NextCommand.COMMAND_WORD:
            return new NextCommand();

        case PrevCommand.COMMAND_WORD:
            return new PrevCommand();

        case LsCommand.COMMAND_WORD:
            return new LsCommand();

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case ConvertCommand.COMMAND_WORD:
            return new ConvertCommandParser().parse(arguments);

        case CreateConvertCommand.COMMAND_WORD:
            return new CreateConvertCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case UndoAllCommand.COMMAND_WORD:
            return new UndoAllCommand();

        case RedoAllCommand.COMMAND_WORD:
            return new RedoAllCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommand();

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case GoogleCommand.COMMAND_WORD:
            return new GoogleCommandParser().parse(arguments);

        case LayerCommand.COMMAND_WORD:
            return new LayerCommandParser().parse(arguments);

        case CanvasCommand.COMMAND_WORD:
            return new CanvasCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
