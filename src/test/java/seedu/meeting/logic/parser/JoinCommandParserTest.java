package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.meeting.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.meeting.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.meeting.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_GROUP_0;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_JOIN_GROUP_TITLE_DESC_GROUP_0;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_JOIN_GROUP_TITLE_DESC_GROUP_2101;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.meeting.testutil.TypicalGroups.GROUP_0;
import static seedu.meeting.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.meeting.logic.commands.JoinCommand;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.GroupBuilder;
import seedu.meeting.testutil.PersonBuilder;


public class JoinCommandParserTest {

    private JoinCommandParser parser = new JoinCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();
        Group expectedGroup = new GroupBuilder().withTitle(GROUP_0.getTitle().fullTitle).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + VALID_JOIN_GROUP_TITLE_DESC_GROUP_0,
                new JoinCommand(expectedPerson, expectedGroup));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                + VALID_JOIN_GROUP_TITLE_DESC_GROUP_0, new JoinCommand(expectedPerson, expectedGroup));

        // multiple group titles - last title accepted
        assertParseSuccess(parser, NAME_DESC_BOB + VALID_JOIN_GROUP_TITLE_DESC_GROUP_2101
                + VALID_JOIN_GROUP_TITLE_DESC_GROUP_0, new JoinCommand(expectedPerson, expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, JoinCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + VALID_JOIN_GROUP_TITLE_DESC_GROUP_0, expectedMessage);

        // missing group prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_GROUP_TITLE_GROUP_0, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_JOIN_GROUP_TITLE_DESC_GROUP_0,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_GROUP_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + VALID_JOIN_GROUP_TITLE_DESC_GROUP_0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, JoinCommand.MESSAGE_USAGE));
    }
}
