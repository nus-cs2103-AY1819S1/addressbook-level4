package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.learnvocabulary.logic.commands.AddCommand;
import seedu.learnvocabulary.logic.commands.ClearCommand;
import seedu.learnvocabulary.logic.commands.Command;
import seedu.learnvocabulary.logic.commands.DeleteCommand;
import seedu.learnvocabulary.logic.commands.EditCommand;
import seedu.learnvocabulary.logic.commands.ExitCommand;
import seedu.learnvocabulary.logic.commands.FindCommand;
import seedu.learnvocabulary.logic.commands.GroupaddCommand;
import seedu.learnvocabulary.logic.commands.GroupdeleteCommand;
import seedu.learnvocabulary.logic.commands.HelpCommand;
import seedu.learnvocabulary.logic.commands.HistoryCommand;
import seedu.learnvocabulary.logic.commands.LearnCommand;
import seedu.learnvocabulary.logic.commands.ListCommand;
import seedu.learnvocabulary.logic.commands.RedoCommand;
import seedu.learnvocabulary.logic.commands.SelectCommand;
import seedu.learnvocabulary.logic.commands.ShowCommand;
import seedu.learnvocabulary.logic.commands.ShowGroupCommand;
import seedu.learnvocabulary.logic.commands.TriviaAnsCommand;
import seedu.learnvocabulary.logic.commands.TriviaCommand;
import seedu.learnvocabulary.logic.commands.UndoCommand;
import seedu.learnvocabulary.logic.commands.WordOfTheDayCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;

/**
 * Parses user input.
 */
public class LearnVocabularyParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user or website input into command for execution.
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

        case LearnCommand.COMMAND_WORD:
            return new LearnCommandParser().parse(arguments);

        /*
        * Word of the day is fetched from website and not user. Parser is needed to parse
        * data from website Dictionary.com into a word object. */
        case WordOfTheDayCommand.COMMAND_WORD:
            return new WordOfTheDayParser().parse("");

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case TriviaCommand.COMMAND_WORD:
            return new TriviaCommand();

        case TriviaAnsCommand.COMMAND_WORD:
            return new TriviaAnsCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

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

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);
            //@@author Harryqu123
        case GroupdeleteCommand.COMMAND_WORD:
            return new GroupDeleteCommandParser().parse(arguments);

        case GroupaddCommand.COMMAND_WORD:
            return new GroupAddCommandParser().parse(arguments);

            case ShowGroupCommand.COMMAND_WORD:
                return new ShowGroupCommandParser().parse(arguments);
//@@author
                default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
