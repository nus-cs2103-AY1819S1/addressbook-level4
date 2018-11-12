package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.superta.logic.commands.AddCommand;
import seedu.superta.logic.commands.AddStudentToTutorialGroupCommand;
import seedu.superta.logic.commands.ClearCommand;
import seedu.superta.logic.commands.Command;
import seedu.superta.logic.commands.CreateAssignmentCommand;
import seedu.superta.logic.commands.CreateAttendanceCommand;
import seedu.superta.logic.commands.CreateTutorialGroupCommand;
import seedu.superta.logic.commands.DeleteAssignmentCommand;
import seedu.superta.logic.commands.DeleteCommand;
import seedu.superta.logic.commands.DeleteTutorialGroupCommand;
import seedu.superta.logic.commands.EditCommand;
import seedu.superta.logic.commands.ExitCommand;
import seedu.superta.logic.commands.FeedbackCommand;
import seedu.superta.logic.commands.FindCommand;
import seedu.superta.logic.commands.GradeAssignmentCommand;
import seedu.superta.logic.commands.HelpCommand;
import seedu.superta.logic.commands.HistoryCommand;
import seedu.superta.logic.commands.ListCommand;
import seedu.superta.logic.commands.ListTutorialGroupsCommand;
import seedu.superta.logic.commands.MarkAttendanceCommand;
import seedu.superta.logic.commands.RedoCommand;
import seedu.superta.logic.commands.RemoveStudentFromTutorialGroupCommand;
import seedu.superta.logic.commands.SelectCommand;
import seedu.superta.logic.commands.UndoCommand;
import seedu.superta.logic.commands.UpdateAssignmentCommand;
import seedu.superta.logic.commands.UpdateTutorialGroupCommand;
import seedu.superta.logic.commands.ViewAssignmentCommand;
import seedu.superta.logic.commands.ViewSessionCommand;
import seedu.superta.logic.commands.ViewStudentFeedbackCommand;
import seedu.superta.logic.commands.ViewTutorialGroupCommand;
import seedu.superta.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SuperTaClientParser {

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

        case CreateTutorialGroupCommand.COMMAND_WORD:
            return new CreateTutorialGroupCommandParser().parse(arguments);

        case UpdateTutorialGroupCommand.COMMAND_WORD:
            return new UpdateTutorialGroupCommandParser().parse(arguments);

        case DeleteTutorialGroupCommand.COMMAND_WORD:
            return new DeleteTutorialGroupCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListTutorialGroupsCommand.COMMAND_WORD:
            return new ListTutorialGroupsCommand();

        case ViewTutorialGroupCommand.COMMAND_WORD:
            return new ViewTutorialGroupCommandParser().parse(arguments);

        case ViewAssignmentCommand.COMMAND_WORD:
            return new ViewAssignmentCommandParser().parse(arguments);

        case CreateAssignmentCommand.COMMAND_WORD:
            return new CreateAssignmentCommandParser().parse(arguments);

        case UpdateAssignmentCommand.COMMAND_WORD:
            return new UpdateAssignmentCommandParser().parse(arguments);

        case CreateAttendanceCommand.COMMAND_WORD:
            return new CreateAttendanceCommandParser().parse(arguments);

        case MarkAttendanceCommand.COMMAND_WORD:
            return new MarkAttendanceCommandParser().parse(arguments);

        case ViewSessionCommand.COMMAND_WORD:
            return new ViewSessionCommandParser().parse(arguments);

        case DeleteAssignmentCommand.COMMAND_WORD:
            return new DeleteAssignmentCommandParser().parse(arguments);

        case AddStudentToTutorialGroupCommand.COMMAND_WORD:
            return new AddStudentToTutorialGroupCommandParser().parse(arguments);

        case RemoveStudentFromTutorialGroupCommand.COMMAND_WORD:
            return new RemoveStudentFromTutorialGroupCommandParser().parse(arguments);

        case GradeAssignmentCommand.COMMAND_WORD:
            return new GradeAssignmentCommandParser().parse(arguments);

        case FeedbackCommand.COMMAND_WORD:
            return new FeedbackCommandParser().parse(arguments);

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

        case ViewStudentFeedbackCommand.COMMAND_WORD:
            return new ViewStudentFeedbackCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
