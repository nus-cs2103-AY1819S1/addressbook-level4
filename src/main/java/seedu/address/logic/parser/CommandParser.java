package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddBulletCommand;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ContextCommand;
import seedu.address.logic.commands.EditEntryInfoCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoadTemplateCommand;
import seedu.address.logic.commands.MakeCommand;
import seedu.address.logic.commands.SelectEntryCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.exceptions.DeleteEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CommandParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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
        case AddEntryCommand.COMMAND_WORD:
            return new AddEntryCommandParser().parse(arguments);

        case DeleteEntryCommand.COMMAND_WORD:
            return new DeleteEntryCommandParser().parse(arguments);

        case AddBulletCommand.COMMAND_WORD:
            return new AddBulletCommandParser().parse(arguments);

        case EditEntryInfoCommand.COMMAND_WORD:
            return new EditEntryInfoCommandParser().parse(arguments);

        case ContextCommand.COMMAND_WORD:
            return new ContextCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case LoadTemplateCommand.COMMAND_WORD:
            return new LoadTemplateCommandParser().parse(arguments);

        case MakeCommand.COMMAND_WORD:
            return new MakeCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case SelectEntryCommand.COMMAND_WORD:
            return new SelectEntryCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
