package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_DESC_WEEKLY;
import static seedu.address.testutil.TypicalGroups.GROUP_2101;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalMeetings.WEEKLY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilepathCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.JoinCommand;
import seedu.address.logic.commands.LeaveCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.util.GroupTitleContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.util.PersonNameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.GroupUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

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

    @Test
    public void parseCommand_addGroup() throws Exception {
        Group group = new GroupBuilder().build();
        AddGroupCommand command = (AddGroupCommand) parser.parseCommand(GroupUtil.getAddGroupCommand(group));
        assertEquals(new AddGroupCommand(group), command);
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
    public void parseCommand_deleteGroup() throws Exception {
        Group group = new GroupBuilder().build();
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(GroupUtil.getDeleteGroupCommand(group));
        assertEquals(new DeleteGroupCommand(group), command);
    }

    @Test
    public void parseCommand_joinGroup() throws Exception {
        Group group = new GroupBuilder().build();
        Person person = new PersonBuilder().build();
        String groupName = GroupUtil.getGroupAsTitle(group);
        String personName = PersonUtil.getPersonName(person);

        JoinCommand command = (JoinCommand) parser.parseCommand(
                JoinCommand.COMMAND_WORD + " " + personName + groupName);
        assertEquals(new JoinCommand(person, group), command);
    }

    @Test
    public void parseCommand_leaveGroup() throws Exception {
        Group group = new GroupBuilder().build();
        Person person = new PersonBuilder().build();
        String groupName = GroupUtil.getGroupAsTitle(group);
        String personName = PersonUtil.getPersonName(person);

        LeaveCommand command = (LeaveCommand) parser.parseCommand(
                LeaveCommand.COMMAND_WORD + " " + personName + groupName);
        assertEquals(new LeaveCommand(person, group), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
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
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_export() throws Exception {
        assertTrue(parser.parseCommand(ExportCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_PATH + "test.xml") instanceof ExportCommand);
    }

    @Test
    public void parseCommand_filepath() throws Exception {
        assertTrue(parser.parseCommand(FilepathCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_PATH + "test.xml") instanceof FilepathCommand);
    }

    @Test
    public void parseCommand_findPersons() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new PersonNameContainsKeywordsPredicate(
            keywords, Collections.emptyList(), Collections.emptyList())), command);
    }

    @Test
    public void parseCommand_findGroups() throws Exception {
        List<String> keywords = Arrays.asList("school", "work", "friends");
        FindGroupCommand command = (FindGroupCommand) parser.parseCommand(
            FindGroupCommand.COMMAND_WORD + " " + FindGroupCommand.FIND_GROUP_PARAM + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindGroupCommand(new GroupTitleContainsKeywordsPredicate(
            keywords, Collections.emptyList(), Collections.emptyList())), command);
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
    public void parseCommand_import() throws Exception {
        assertTrue(parser.parseCommand(ImportCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_PATH + "test.xml") instanceof ImportCommand);

    }
    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + ListCommand.COMMAND_PARAM_GROUP)
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + ListCommand.COMMAND_PARAM_PERSON)
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + ListCommand.COMMAND_PARAM_MEETING)
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + ListCommand.COMMAND_PARAM_GROUP_SHORT)
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + ListCommand.COMMAND_PARAM_PERSON_SHORT)
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " "
            + ListCommand.COMMAND_PARAM_MEETING_SHORT) instanceof ListCommand);
    }

    @Test
    public void parseCommand_selectPerson() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " p/" + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON), command);
    }

    @Test
    public void parseCommand_selectGroup() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " g/" + INDEX_FIRST_GROUP.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_meetCommand() throws Exception {
        parser.parseCommand(MeetCommand.COMMAND_WORD + " " + GROUP_2101.getTitle() + " "
                + VALID_MEETING_DESC_WEEKLY);

        MeetCommand expectedCommand = new MeetCommand(GROUP_2101, WEEKLY);
    }

    @Test
    public void parseCommand_cancelCommand() throws Exception {
        parser.parseCommand(CancelCommand.COMMAND_WORD + " " + GROUP_2101.getTitle());

        CancelCommand expectedCommand = new CancelCommand(GROUP_2101);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
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
