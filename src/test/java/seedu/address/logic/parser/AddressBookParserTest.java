package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.PersonBuilder.DEFAULT_STORED_LOCATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.DeleteTimetableCommand;
import seedu.address.logic.commands.DownloadTimetableCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditTimetableCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser(true);


    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommandAddTimetableFromStoredLocation() throws Exception {
        AddTimetableCommand command = (AddTimetableCommand) parser
            .parseCommand(AddTimetableCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        AddTimetableCommand expectedCommand = new AddTimetableCommand(INDEX_FIRST, null);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandAddTimetableFromOtherLocation() throws Exception {
        Person person = new PersonBuilder().withStoredLocation(DEFAULT_STORED_LOCATION).build();
        person.getTimetable().downloadTimetableAsCsv();
        String location = person.getStoredLocation();
        AddTimetableCommand command = (AddTimetableCommand) parser
            .parseCommand(
                AddTimetableCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " fl/"
                    + location);
        AddTimetableCommand expectedCommand = new AddTimetableCommand(INDEX_FIRST, location);
        File timetableToDelete = new File(location);
        if (timetableToDelete.exists()) {
            timetableToDelete.delete();
        }
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommandDeleteTimetable() throws Exception {
        DeleteTimetableCommand command = (DeleteTimetableCommand) parser
            .parseCommand(DeleteTimetableCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        DeleteTimetableCommand expectedCommand = new DeleteTimetableCommand(INDEX_FIRST);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandDownloadTimetable() throws Exception {
        DownloadTimetableCommand command = (DownloadTimetableCommand) parser
            .parseCommand(DownloadTimetableCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        DownloadTimetableCommand expectedCommand = new DownloadTimetableCommand(INDEX_FIRST);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST.getOneBased() + " " + PersonUtil
            .getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommandEditTimetable() throws Exception {
        EditTimetableCommand command = (EditTimetableCommand) parser
            .parseCommand(EditTimetableCommand.COMMAND_WORD
                + " " + INDEX_FIRST.getOneBased()
                + " day/friday timing/1100 m/cs2103");
        EditTimetableCommand expectedCommand = new EditTimetableCommand(INDEX_FIRST, "friday",
            "1100", "cs2103");
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Arrays.asList("Alex");
        List<String> phoneKeywords = Arrays.asList("91234567");
        List<String> addressKeywords = Arrays.asList("Orchard");
        List<String> emailKeywords = Arrays.asList("alex@example.com");
        List<String> tagKeywords = Arrays.asList("friends", "Boy");

        //List<String> keywords = Arrays.asList("foo", "bar", "baz");
        List<String> keywords = Arrays.asList("n/Alex", "p/91234567", "a/Orchard",
                "e/alex@example.com", "t/friends Boy");

        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        //assertEquals(new FindCommand(new NameContainsKeywordsPredicate<Person>(keywords)), command);
        assertEquals(new FindCommand(nameKeywords, phoneKeywords, addressKeywords,
                emailKeywords, tagKeywords), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(
            parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
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
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommandAddGroup() throws Exception {
        AddGroupCommand command = (AddGroupCommand) parser.parseCommand(AddGroupCommand.COMMAND_WORD
                + " " + PREFIX_NAME + GroupBuilder.DEFAULT_NAME
                + " " + PREFIX_DESCRIPTION + GroupBuilder.DEFAULT_DESCRIPTION);
        Group group = new GroupBuilder().withName(GroupBuilder.DEFAULT_NAME)
                .withDescription(GroupBuilder.DEFAULT_DESCRIPTION).build();
        AddGroupCommand expectedCommand = new AddGroupCommand(group);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandDeleteGroup() throws Exception {
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(DeleteGroupCommand.COMMAND_WORD
                + " " + PREFIX_NAME + GroupBuilder.DEFAULT_NAME);
        Group group = new GroupBuilder().withName(GroupBuilder.DEFAULT_NAME).build();
        DeleteGroupCommand expectedCommand = new DeleteGroupCommand(group);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandEditGroup() throws Exception {
        EditGroupCommand command = (EditGroupCommand) parser.parseCommand(EditGroupCommand.COMMAND_WORD
                + " Friend Group " + PREFIX_NAME + GroupBuilder.DEFAULT_NAME
                + " " + PREFIX_DESCRIPTION + GroupBuilder.DEFAULT_DESCRIPTION);
        Name oldName = new Name("Friend Group");
        EditGroupCommand.EditGroupDescriptor editGroupDescriptor = new EditGroupCommand.EditGroupDescriptor();
        editGroupDescriptor.setName(new Name(GroupBuilder.DEFAULT_NAME));
        editGroupDescriptor.setDescription(GroupBuilder.DEFAULT_DESCRIPTION);
        EditGroupCommand expectedCommand = new EditGroupCommand(oldName, editGroupDescriptor);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandRegister() throws Exception {
        RegisterCommand command = (RegisterCommand) parser.parseCommand(RegisterCommand.COMMAND_WORD
                + " 1"
                + " " + PREFIX_NAME + GroupBuilder.DEFAULT_NAME);
        RegisterCommand expectedCommand = new RegisterCommand(new Name(GroupBuilder.DEFAULT_NAME), INDEX_FIRST);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandDeleteMember() throws Exception {
        DeleteMemberCommand command = (DeleteMemberCommand) parser.parseCommand(DeleteMemberCommand.COMMAND_WORD
                + " 1"
                + " " + PREFIX_NAME + GroupBuilder.DEFAULT_NAME);
        DeleteMemberCommand expectedCommand = new DeleteMemberCommand(new Name(GroupBuilder.DEFAULT_NAME), INDEX_FIRST);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommandViewGroup() throws Exception {
        ViewGroupCommand command = (ViewGroupCommand) parser.parseCommand(ViewGroupCommand.COMMAND_WORD
                + " " + PREFIX_NAME + GroupBuilder.DEFAULT_NAME);
        ViewGroupCommand expectedCommand = new ViewGroupCommand(new Name(GroupBuilder.DEFAULT_NAME));
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
