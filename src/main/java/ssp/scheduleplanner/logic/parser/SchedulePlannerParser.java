package ssp.scheduleplanner.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.AddCategoryCommand;
import ssp.scheduleplanner.logic.commands.AddCommand;
import ssp.scheduleplanner.logic.commands.AddRepeatCommand;
import ssp.scheduleplanner.logic.commands.AddTagCommand;
import ssp.scheduleplanner.logic.commands.ArchiveCommand;
import ssp.scheduleplanner.logic.commands.ClearCategoryCommand;
import ssp.scheduleplanner.logic.commands.ClearCommand;
import ssp.scheduleplanner.logic.commands.Command;
import ssp.scheduleplanner.logic.commands.DeleteCommand;
import ssp.scheduleplanner.logic.commands.EditCategoryCommand;
import ssp.scheduleplanner.logic.commands.EditCommand;
import ssp.scheduleplanner.logic.commands.ExitCommand;
import ssp.scheduleplanner.logic.commands.FilterCommand;
import ssp.scheduleplanner.logic.commands.FilterStrictCommand;
import ssp.scheduleplanner.logic.commands.FindCommand;
import ssp.scheduleplanner.logic.commands.FirstDayCommand;
import ssp.scheduleplanner.logic.commands.HelpCommand;
import ssp.scheduleplanner.logic.commands.HistoryCommand;
import ssp.scheduleplanner.logic.commands.ListArchivedCommand;
import ssp.scheduleplanner.logic.commands.ListCommand;
import ssp.scheduleplanner.logic.commands.ListDayCommand;
import ssp.scheduleplanner.logic.commands.ListMonthCommand;
import ssp.scheduleplanner.logic.commands.ListOverdueCommand;
import ssp.scheduleplanner.logic.commands.ListWeekCommand;
import ssp.scheduleplanner.logic.commands.ProgressTodayCommand;
import ssp.scheduleplanner.logic.commands.ProgressWeekCommand;
import ssp.scheduleplanner.logic.commands.RedoCommand;
import ssp.scheduleplanner.logic.commands.RemoveCategoryCommand;
import ssp.scheduleplanner.logic.commands.SelectCommand;
import ssp.scheduleplanner.logic.commands.UndoCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SchedulePlannerParser {

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddCategoryCommand.COMMAND_WORD:
            return new AddCategoryCommandParser().parse(arguments);

        case EditCategoryCommand.COMMAND_WORD:
            return new EditCategoryCommandParser().parse(arguments);

        case RemoveCategoryCommand.COMMAND_WORD:
            return new RemoveCategoryCommandParser().parse(arguments);

        case ClearCategoryCommand.COMMAND_WORD:
            return new ClearCategoryCommandParser().parse(arguments);

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case AddRepeatCommand.COMMAND_WORD:
            return new AddRepeatCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FirstDayCommand.COMMAND_WORD:
            return new FirstDayCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case FilterStrictCommand.COMMAND_WORD:
            return new FilterStrictCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListArchivedCommand.COMMAND_WORD:
            return new ListArchivedCommand();

        case ListDayCommand.COMMAND_WORD:
            return new ListDayCommand();

        case ListMonthCommand.COMMAND_WORD:
            return new ListMonthCommand();

        case ListOverdueCommand.COMMAND_WORD:
            return new ListOverdueCommand();

        case ListWeekCommand.COMMAND_WORD:
            return new ListWeekCommand();

        case ProgressTodayCommand.COMMAND_WORD:
            return new ProgressTodayCommand();

        case ProgressWeekCommand.COMMAND_WORD:
            return new ProgressWeekCommand();

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

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
