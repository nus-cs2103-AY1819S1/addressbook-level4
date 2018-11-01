package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMedicalRecordCommand;
import seedu.address.logic.commands.AddMedicineCommand;
import seedu.address.logic.commands.CheckStockCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DispenseMedicineCommand;
import seedu.address.logic.commands.DisplayQueueCommand;
import seedu.address.logic.commands.DisplayServedPatientsCommand;
import seedu.address.logic.commands.DocumentContentAddCommand;
import seedu.address.logic.commands.DocumentContentDisplayCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditMedicineCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindMedicineCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListStockCommand;
import seedu.address.logic.commands.MedicalCertificateCommand;
import seedu.address.logic.commands.PaymentCommand;
import seedu.address.logic.commands.ReceiptCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReferralLetterCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.RestockCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.parser.exceptions.ParseException;

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
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AddMedicineCommand.COMMAND_WORD:
        case AddMedicineCommand.COMMAND_ALIAS:
            return new AddMedicineCommandParser().parse(arguments);

        case DispenseMedicineCommand.COMMAND_WORD:
        case DispenseMedicineCommand.COMMAND_ALIAS:
            return new DispenseMedicineCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case EditMedicineCommand.COMMAND_WORD:
        case EditMedicineCommand.COMMAND_ALIAS:
            return new EditMedicineCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case CheckStockCommand.COMMAND_WORD:
        case CheckStockCommand.COMMAND_ALIAS:
            return new CheckStockCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindMedicineCommand.COMMAND_WORD:
        case FindMedicineCommand.COMMAND_ALIAS:
            return new FindMedicineCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case ListStockCommand.COMMAND_WORD:
        case ListStockCommand.COMMAND_ALIAS:
            return new ListStockCommand();

        case RestockCommand.COMMAND_WORD:
        case RestockCommand.COMMAND_ALIAS:
            return new RestockCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case AddMedicalRecordCommand.COMMAND_WORD:
        case AddMedicalRecordCommand.COMMAND_ALIAS:
            return new AddMedicalRecordCommandParser().parse(arguments);

        case ReceiptCommand.COMMAND_WORD:
        case ReceiptCommand.COMMAND_ALIAS:
            return new ReceiptCommandParser().parse(arguments);

        case MedicalCertificateCommand.COMMAND_WORD:
            return new MedicalCertificateCommandParser().parse(arguments);

        case ReferralLetterCommand.COMMAND_WORD:
        case ReferralLetterCommand.COMMAND_ALIAS:
            return new ReferralLetterCommandParser().parse(arguments);

        case DisplayQueueCommand.COMMAND_WORD:
        case DisplayQueueCommand.COMMAND_ALIAS:
            return new DisplayQueueCommand();

        case DisplayServedPatientsCommand.COMMAND_WORD:
        case DisplayServedPatientsCommand.COMMAND_ALIAS:
            return new DisplayServedPatientsCommand();

        case RegisterCommand.COMMAND_WORD:
        case RegisterCommand.COMMAND_ALIAS:
            return new RegisterCommandParser().parse(arguments);

        case InsertCommand.COMMAND_WORD:
        case InsertCommand.COMMAND_ALIAS:
            return new InsertCommandParser().parse(arguments);

        case RemoveCommand.COMMAND_WORD:
        case RemoveCommand.COMMAND_ALIAS:
            return new RemoveCommandParser().parse(arguments);

        case ServeCommand.COMMAND_WORD:
        case ServeCommand.COMMAND_ALIAS:
            return new ServeCommand();

        case DocumentContentAddCommand.COMMAND_WORD:
        case DocumentContentAddCommand.COMMAND_ALIAS:
            return new DocumentContentAddCommandParser().parse(arguments);

        case DocumentContentDisplayCommand.COMMAND_WORD:
        case DocumentContentDisplayCommand.COMMAND_ALIAS:
            return new DocumentContentDisplayCommand();

        case FinishCommand.COMMAND_WORD:
            return new FinishCommand();

        case PaymentCommand.COMMAND_WORD:
        case PaymentCommand.COMMAND_ALIAS:
            return new PaymentCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
