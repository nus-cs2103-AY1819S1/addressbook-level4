package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT_TO_DISPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DispenseMedicineCommand;
import seedu.address.logic.commands.DisplayServedPatientsCommand;
import seedu.address.logic.commands.DocumentContentAddCommand;
import seedu.address.logic.commands.DocumentContentDisplayCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MedicalCertificateCommand;
import seedu.address.logic.commands.PaymentCommand;
import seedu.address.logic.commands.ReceiptCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReferralLetterCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Patient;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Patient patient = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(patient));
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Patient patient = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(patient));
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_dispenseMedicine() throws Exception {
        DispenseMedicineCommand command = (DispenseMedicineCommand) parser.parseCommand(
                DispenseMedicineCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_AMOUNT_TO_DISPENSE + "123");
        assertEquals(command, new DispenseMedicineCommand(INDEX_FIRST_PERSON, new QuantityToDispense(123)));
    }

    @Test
    public void parseCommand_dispenseMedicineAlias() throws Exception {
        DispenseMedicineCommand command = (DispenseMedicineCommand) parser.parseCommand(
                DispenseMedicineCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_AMOUNT_TO_DISPENSE + "123");
        assertEquals(command, new DispenseMedicineCommand(INDEX_FIRST_PERSON, new QuantityToDispense(123)));
    }

    @Test
    public void parseCommand_displayServedPatients() throws Exception {
        assertTrue(parser.parseCommand(DisplayServedPatientsCommand.COMMAND_WORD)
                instanceof DisplayServedPatientsCommand);
        assertTrue(parser.parseCommand(DisplayServedPatientsCommand.COMMAND_WORD + " 3")
                instanceof DisplayServedPatientsCommand);
    }

    @Test
    public void parseCommand_displayServedPatientsAlias() throws Exception {
        assertTrue(parser.parseCommand(DisplayServedPatientsCommand.COMMAND_ALIAS)
                instanceof DisplayServedPatientsCommand);
        assertTrue(parser.parseCommand(DisplayServedPatientsCommand.COMMAND_WORD + " 3")
                instanceof DisplayServedPatientsCommand);
    }

    @Test
    public void parseCommand_documentAdd() throws Exception {
        DocumentContentAddCommand command = (DocumentContentAddCommand) parser.parseCommand(
                DocumentContentAddCommand.COMMAND_WORD + " n/Hello mc/5 r/NUH");
        DocumentContentAddCommand.DocumentContentDescriptor descriptor =
                new DocumentContentAddCommand.DocumentContentDescriptor();
        descriptor.setMcContent("5");
        descriptor.setNoteContent("Hello");
        descriptor.setReferralContent("NUH");
        assertEquals(new DocumentContentAddCommand(descriptor), command);
    }

    @Test
    public void parseCommand_documentAddAlias() throws Exception {
        DocumentContentAddCommand command = (DocumentContentAddCommand) parser.parseCommand(
                DocumentContentAddCommand.COMMAND_ALIAS + " n/Hello mc/5 r/NUH");
        DocumentContentAddCommand.DocumentContentDescriptor descriptor =
                new DocumentContentAddCommand.DocumentContentDescriptor();
        descriptor.setMcContent("5");
        descriptor.setNoteContent("Hello");
        descriptor.setReferralContent("NUH");
        assertEquals(new DocumentContentAddCommand(descriptor), command);
    }

    @Test
    public void parseCommand_documentDisplay() throws Exception {
        assertTrue(parser.parseCommand(DocumentContentDisplayCommand.COMMAND_WORD)
                instanceof DocumentContentDisplayCommand);
        assertTrue(parser.parseCommand(DocumentContentDisplayCommand.COMMAND_WORD + " 3")
                instanceof DocumentContentDisplayCommand);
    }

    @Test
    public void parseCommand_documentDisplayAlias() throws Exception {
        assertTrue(parser.parseCommand(DocumentContentDisplayCommand.COMMAND_ALIAS)
                instanceof DocumentContentDisplayCommand);
        assertTrue(parser.parseCommand(DocumentContentDisplayCommand.COMMAND_ALIAS + " 3")
                instanceof DocumentContentDisplayCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Patient patient = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Patient patient = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_finish() throws Exception {
        assertTrue(parser.parseCommand(FinishCommand.COMMAND_WORD) instanceof FinishCommand);
        assertTrue(parser.parseCommand("finish 5") instanceof FinishCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_insert() throws Exception {
        InsertCommand command = (InsertCommand) parser.parseCommand(
                InsertCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_POSITION + "2");
        assertEquals(new InsertCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), command);
    }

    @Test
    public void parseCommand_insertAlias() throws Exception {
        InsertCommand command = (InsertCommand) parser.parseCommand(
                InsertCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_POSITION + "2");
        assertEquals(new InsertCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_medicalCertificate() throws Exception {
        MedicalCertificateCommand command = (MedicalCertificateCommand) parser.parseCommand(
                MedicalCertificateCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new MedicalCertificateCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_payment() throws Exception {
        PaymentCommand command = (PaymentCommand) parser.parseCommand(
                PaymentCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PaymentCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_paymentAlias() throws Exception {
        PaymentCommand command = (PaymentCommand) parser.parseCommand(
                PaymentCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PaymentCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_receipt() throws Exception {
        ReceiptCommand command = (ReceiptCommand) parser.parseCommand(
                ReceiptCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ReceiptCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_receiptAlias() throws Exception {
        ReceiptCommand command = (ReceiptCommand) parser.parseCommand(
                ReceiptCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ReceiptCommand(INDEX_FIRST_PERSON), command);
    }


    @Test
    public void parseCommand_referralLetter() throws Exception {
        ReferralLetterCommand command = (ReferralLetterCommand) parser.parseCommand(
                ReferralLetterCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ReferralLetterCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_register() throws Exception {
        RegisterCommand command = (RegisterCommand) parser.parseCommand(
                RegisterCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RegisterCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_registerAlias() throws Exception {
        RegisterCommand command = (RegisterCommand) parser.parseCommand(
                RegisterCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RegisterCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_remove() throws Exception {
        RemoveCommand command = (RemoveCommand) parser.parseCommand(
                RemoveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RemoveCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_removeAlias() throws Exception {
        RemoveCommand command = (RemoveCommand) parser.parseCommand(
                RemoveCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RemoveCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_serve() throws Exception {
        assertTrue(parser.parseCommand(ServeCommand.COMMAND_WORD) instanceof ServeCommand);
        assertTrue(parser.parseCommand("serve 1") instanceof ServeCommand);
    }

    @Test
    public void parseCommand_serveAlias() throws Exception {
        assertTrue(parser.parseCommand(ServeCommand.COMMAND_ALIAS) instanceof ServeCommand);
        assertTrue(parser.parseCommand("ser 1") instanceof ServeCommand);
    }
    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
