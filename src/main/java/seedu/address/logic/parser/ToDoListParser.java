package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.commands.CommandToDo;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.HelpToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * ToDoList parses user input.
 */
public class ToDoListParser {

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
    public CommandToDo toDoParseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpToDoCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        System.out.printf("commandWord = %s\n",commandWord);
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

            case AddToDoCommand.COMMAND_WORD:
                return new AddToDoCommandParser().parse(arguments);

            case DeleteToDoCommand.COMMAND_WORD:
                return new DeleteToDoCommandParser().parse(arguments);

            case HelpToDoCommand.COMMAND_WORD:
                return new HelpToDoCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
