package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Context;

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
     * @param contextId the current context
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, String contextId) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Shared Commands
        switch (commandWord) {
        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);
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
            break;
        }

        // Executes commands for events
        if (contextId.equals(Context.EVENT_CONTEXT_ID)) {
            return parseEventCommand(commandWord, arguments);
        }

        // Executes commands for volunteers
        if (contextId.equals(Context.VOLUNTEER_CONTEXT_ID)) {
            return parseVolunteerCommand(commandWord, arguments);
        }

        // Execute commands for records
        if (contextId.equals(Context.RECORD_CONTEXT_ID)) {
            return parseRecordCommand(commandWord, arguments);
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Parses user input specifically for event commands for execution.
     **/
    private Command parseEventCommand(String commandWord, String arguments) throws ParseException {
        // Replace all these commands
        switch (commandWord) {
        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

        case SelectEventCommand.COMMAND_WORD:
            return new SelectEventCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case ListEventCommand.COMMAND_WORD:
            return new ListEventCommand();

        case OverviewCommand.COMMAND_WORD:
            return new OverviewCommand();

        case ManageCommand.COMMAND_WORD:
            return new ManageCommandParser().parse(arguments);

        case ExportEventXmlCommand.COMMAND_WORD:
            return new ExportEventXmlCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input specifically for volunteer commands for execution.
     */
    private Command parseVolunteerCommand(String commandWord, String arguments) throws ParseException {
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

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case OverviewCommand.COMMAND_WORD:
            return new OverviewCommand();

        case ExportCertCommand.COMMAND_WORD:
            return new ExportCertCommandParser().parse(arguments);

        case ExportVolunteerCsvCommand.COMMAND_WORD:
            return new ExportVolunteerCsvCommandParser().parse(arguments);

        case ExportVolunteerXmlCommand.COMMAND_WORD:
            return new ExportVolunteerXmlCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input specifically for record management commands for execution.
     **/
    private Command parseRecordCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case AddRecordCommand.COMMAND_WORD:
            return new AddRecordCommandParser().parse(arguments);

        case EditRecordCommand.COMMAND_WORD:
            return new EditRecordCommandParser().parse(arguments);

        case DeleteRecordCommand.COMMAND_WORD:
            return new DeleteRecordCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
