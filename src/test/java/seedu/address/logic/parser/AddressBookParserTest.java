package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_FIRST_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CONTACT_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalTags.APPOINTMENT_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddEventTagCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditEventAddressCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventTagMatchesKeywordsPredicate;
import seedu.address.model.filereader.FileReader;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.FileReaderBuilder;
import seedu.address.testutil.FileReaderUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ScheduledEventBuilder;
import seedu.address.testutil.ScheduledEventUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    // test for add command alias
    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command =
                (AddCommand) parser.parseCommand(PersonUtil.getAddCommandAlias(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_edit_alias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exit_alias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_find_alias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new EventTagMatchesKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_help_alias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD_ALIAS + " 3") instanceof HelpCommand);
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
    public void parseCommand_history_alias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            // assert if the above command throws an exception as expected
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listEvent() throws Exception {
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD) instanceof ListEventCommand);
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD + " 3") instanceof ListEventCommand);
    }

    @Test
    public void parseCommand_list_alias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_select_alias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new ScheduledEventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(ScheduledEventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event,
                new HashSet<>(Arrays.asList(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1))))),
                command);
    }

    @Test
    public void parseCommand_addEventTag() throws Exception {
        AddEventTagCommand command =
                (AddEventTagCommand) parser.parseCommand(AddEventTagCommand.COMMAND_WORD
                        + TAG_DESC_APPOINTMENT);
        assertEquals(new AddEventTagCommand(new HashSet<>(Arrays.asList(APPOINTMENT_TAG))), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC);
        assertEquals(new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_editEventAddress() throws Exception {
        EditEventAddressCommand command = (EditEventAddressCommand) parser.parseCommand(
                EditEventAddressCommand.COMMAND_WORD
                + EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT);
        assertEquals(new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandWordAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD_ALIAS + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_importContactsCommand() throws Exception {
        FileReader fileReader = new FileReaderBuilder().build();
        ImportContactsCommand command = (ImportContactsCommand) parser.parseCommand(
                FileReaderUtil.getImportCommand(fileReader));

        assertEquals(new ImportContactsCommand(fileReader), command);
    }

    @Test
    public void parseCommand_undoCommandWordAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD_ALIAS + " 3") instanceof UndoCommand);
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
