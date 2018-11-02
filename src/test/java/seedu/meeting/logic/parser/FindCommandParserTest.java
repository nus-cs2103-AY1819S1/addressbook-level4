package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.meeting.logic.commands.FindGroupCommand;
import seedu.meeting.logic.commands.FindMeetingCommand;
import seedu.meeting.logic.commands.FindPersonCommand;
import seedu.meeting.model.group.util.GroupTitleContainsKeywordsPredicate;
import seedu.meeting.model.meeting.util.MeetingTitleContainsKeywordsPredicate;
import seedu.meeting.model.person.util.PersonNameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_findPerson_noPrefixUsageDefaultToAllPrefixBehaviour() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindPersonCommand =
                new FindPersonCommand(new PersonNameContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"), Collections.emptyList(), Collections.emptyList()));

        // long form
        assertParseSuccess(parser, "person Alice Bob", expectedFindPersonCommand);
        // short form
        assertParseSuccess(parser, "p Alice Bob", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        // long form
        assertParseSuccess(parser, "\nperson \n Alice \n \t Bob  \t", expectedFindPersonCommand);
        // short form
        assertParseSuccess(parser, " p \n Alice \n \t Bob  \t", expectedFindPersonCommand);
    }

    @Test
    public void parse_findPerson_prefixUsage() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindPersonCommand =
                new FindPersonCommand(new PersonNameContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        Arrays.asList("Charlie", "David"),
                        Arrays.asList("Earl", "Grey")));

        // long form
        assertParseSuccess(parser, "person a/Alice Bob s/Charlie David n/Earl Grey",
            expectedFindPersonCommand);
        // short form
        assertParseSuccess(parser, "p a/Alice Bob s/Charlie David n/Earl Grey", expectedFindPersonCommand);
    }

    @Test
    public void parse_findGroup_noPrefixUsageDefaultToAllPrefixBehaviour() {
        // no leading and trailing whitespaces
        FindGroupCommand expectedFindGroupCommand =
            new FindGroupCommand(new GroupTitleContainsKeywordsPredicate(
                Arrays.asList("Alpha", "Beta"), Collections.emptyList(), Collections.emptyList()));

        // long form
        assertParseSuccess(parser, "group Alpha Beta", expectedFindGroupCommand);
        // short form
        assertParseSuccess(parser, "g Alpha Beta", expectedFindGroupCommand);

        // multiple whitespaces between keywords
        // long form
        assertParseSuccess(parser, "group \n Alpha \n \t Beta  \t", expectedFindGroupCommand);
        // short form
        assertParseSuccess(parser, "g \n Alpha \n \t Beta  \t", expectedFindGroupCommand);
    }

    @Test
    public void parse_findGroup_prefixUsage() {
        // no leading and trailing whitespaces
        FindGroupCommand expectedFindGroupCommand =
            new FindGroupCommand(new GroupTitleContainsKeywordsPredicate(
                Arrays.asList("Alpha", "Beta"),
                Arrays.asList("Caviar", "Delta"),
                Arrays.asList("Echo", "Greek")));

        // long form
        assertParseSuccess(parser, "group a/Alpha Beta s/Caviar Delta n/Echo Greek",
            expectedFindGroupCommand);
        // short form
        assertParseSuccess(parser, "g a/Alpha Beta s/Caviar Delta n/Echo Greek", expectedFindGroupCommand);
    }

    @Test
    public void parse_findMeeting_noPrefixUsageDefaultToAllPrefixBehaviour() {
        // no leading and trailing whitespaces
        FindMeetingCommand expectedFindMeetingCommand =
            new FindMeetingCommand(new MeetingTitleContainsKeywordsPredicate(
                Arrays.asList("Alpha", "Beta"), Collections.emptyList(), Collections.emptyList()));

        // long form
        assertParseSuccess(parser, "meeting Alpha Beta", expectedFindMeetingCommand);
        // short form
        assertParseSuccess(parser, "m Alpha Beta", expectedFindMeetingCommand);

        // multiple whitespaces between keywords
        // long form
        assertParseSuccess(parser, "meeting \n Alpha \n \t Beta  \t", expectedFindMeetingCommand);
        // short form
        assertParseSuccess(parser, "m \n Alpha \n \t Beta  \t", expectedFindMeetingCommand);
    }

    @Test
    public void parse_findMeeting_prefixUsage() {
        // no leading and trailing whitespaces
        FindMeetingCommand expectedFindMeetingCommand =
            new FindMeetingCommand(new MeetingTitleContainsKeywordsPredicate(
                Arrays.asList("Alpha", "Beta"),
                Arrays.asList("Caviar", "Delta"),
                Arrays.asList("Echo", "Greek")));

        // long form
        assertParseSuccess(parser, "meeting a/Alpha Beta s/Caviar Delta n/Echo Greek",
            expectedFindMeetingCommand);
        // short form
        assertParseSuccess(parser, "m a/Alpha Beta s/Caviar Delta n/Echo Greek", expectedFindMeetingCommand);
    }
}
