package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.LearnCommand;
import seedu.address.logic.commands.MatchTestCommand;
import seedu.address.logic.commands.ModeCommand;
import seedu.address.logic.commands.OpenEndedTestCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResearchCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TestCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.state.State;

/**
 * Parses user input.
 */
public class AddressBookParser {
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
    public Command parseCommand(String userInput, State appState) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (appState) {

        case LEARN:
            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case ResearchCommand.COMMAND_WORD:
                return new ResearchCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case OpenEndedTestCommand.COMMAND_WORD:
                return new OpenEndedTestCommandParser().parse(arguments);

            case MatchTestCommand.COMMAND_WORD:
                return new MatchTestCommandParser().parse(arguments);

            case ImportCommand.COMMAND_WORD:
                return new ImportCommandParser().parse(arguments);

                /* commands for navigation */
            case LearnCommand.COMMAND_WORD:
                return new LearnCommandParser().parse(arguments);

            case TestCommand.COMMAND_WORD:
                return new TestCommand(arguments);

            case ReviewCommand.COMMAND_WORD:
                return new ReviewCommand(arguments);

                /* commands for UI change */
            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case TEST:
            switch (commandWord) {
            case OpenEndedTestCommand.COMMAND_WORD:
                return new OpenEndedTestCommandParser().parse(arguments);

            case MatchTestCommand.COMMAND_WORD:
                return new MatchTestCommandParser().parse(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

                /* commands for navigation */
            case LearnCommand.COMMAND_WORD:
                return new LearnCommandParser().parse(arguments);

            case TestCommand.COMMAND_WORD:
                return new TestCommand(arguments);

            case ReviewCommand.COMMAND_WORD:
                return new ReviewCommand(arguments);

                /* commands for UI change */
            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case REVIEW:
            switch (commandWord) {
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

                /* commands for navigation */
            case LearnCommand.COMMAND_WORD:
                return new LearnCommandParser().parse(arguments);

            case TestCommand.COMMAND_WORD:
                return new TestCommand(arguments);

            case ReviewCommand.COMMAND_WORD:
                return new ReviewCommand(arguments);

                /* commands for UI change */
            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case MATCH_TEST:
            switch (commandWord) {

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                return new MatchCommandParser().parse(userInput);
            }

        case MATCH_TEST_RESULT:
            switch(commandWord) {

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case OPEN_ENDED_TEST_QUESTION:
            switch(commandWord) {

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                return new OpenEndedAnswerParser().parse(userInput);
            }

        case OPEN_ENDED_TEST_ANSWER:
            switch(commandWord) {

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                return new OpenEndedCommandParser().parse(userInput);
            }

        case OPEN_ENDED_TEST_RESULT:
            switch(commandWord) {

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ModeCommand.COMMAND_WORD:
                return new ModeCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
