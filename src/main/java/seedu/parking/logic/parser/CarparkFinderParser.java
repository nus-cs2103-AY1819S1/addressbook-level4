package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_HELP_OR_HISTORY_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.parking.logic.commands.CalculateCommand;
import seedu.parking.logic.commands.ClearCommand;
import seedu.parking.logic.commands.Command;
import seedu.parking.logic.commands.ExitCommand;
import seedu.parking.logic.commands.FilterCommand;
import seedu.parking.logic.commands.FindCommand;
import seedu.parking.logic.commands.HelpCommand;
import seedu.parking.logic.commands.HistoryCommand;
import seedu.parking.logic.commands.ListCommand;
import seedu.parking.logic.commands.NotifyCommand;
import seedu.parking.logic.commands.QueryCommand;
import seedu.parking.logic.commands.RedoCommand;
import seedu.parking.logic.commands.SelectCommand;
import seedu.parking.logic.commands.UndoCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CarparkFinderParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution. It first checks
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandWord.equals("f") || commandWord.equals("fi")) {
            throw new ParseException(MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND);
        } else if (commandWord.equals("h")) {
            throw new ParseException(MESSAGE_UNCERTAIN_HELP_OR_HISTORY_COMMAND);
        } else if (commandWord.equals("c")) {
            throw new ParseException(MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND);
        } else if (containsFromFirstLetter(SelectCommand.COMMAND_WORD, commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (containsFromFirstLetter(ClearCommand.COMMAND_WORD, commandWord)) {
            return new ClearCommand();
        } else if (containsFromFirstLetter(FindCommand.COMMAND_WORD, commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (containsFromFirstLetter(ListCommand.COMMAND_WORD, commandWord)) {
            return new ListCommand();
        } else if (containsFromFirstLetter(HistoryCommand.COMMAND_WORD, commandWord)) {
            return new HistoryCommand();
        } else if (containsFromFirstLetter(ExitCommand.COMMAND_WORD, commandWord)) {
            return new ExitCommand();
        } else if (containsFromFirstLetter(HelpCommand.COMMAND_WORD, commandWord)) {
            return new HelpCommand();
        } else if (containsFromFirstLetter(UndoCommand.COMMAND_WORD, commandWord)) {
            return new UndoCommand();
        } else if (containsFromFirstLetter(RedoCommand.COMMAND_WORD, commandWord)) {
            return new RedoCommand();
        } else if (containsFromFirstLetter(FilterCommand.COMMAND_WORD, commandWord)) {
            return new FilterCommandParser().parse(arguments);
        } else if (containsFromFirstLetter(CalculateCommand.COMMAND_WORD, commandWord)) {
            return new CalculateCommandParser().parse(arguments);
        } else if (containsFromFirstLetter(QueryCommand.COMMAND_WORD, commandWord)) {
            return new QueryCommand();
        } else if (containsFromFirstLetter(NotifyCommand.COMMAND_WORD, commandWord)) {
            return new NotifyCommandParser().parse(arguments);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if this system command word contains the user input from index 0
     *
     * @param completeCommand system complete command word
     * @param actualCommand actual command input by the user
     * @return true if system command word contains the user input from index 0
     * and false otherwise.
     */
    public static boolean containsFromFirstLetter(String completeCommand,
        String actualCommand) {
        return completeCommand.indexOf(actualCommand) == 0
            && completeCommand.contains(actualCommand);
    }

}
