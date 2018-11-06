package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.AddEventTagCommand;
import seedu.address.model.tag.Tag;

public class AddEventTagCommandParserTest {
    private AddEventTagCommandParser parser = new AddEventTagCommandParser();

    @Test
    public void parse_fieldValuesPresent_success() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag(VALID_TAG_APPOINTMENT));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_APPOINTMENT,
                new AddEventTagCommand(expectedTags));

        // one tag - accepted
        assertParseSuccess(parser, TAG_DESC_APPOINTMENT,
                new AddEventTagCommand(expectedTags));

        // multiple tags - all accepted
        expectedTags.add(new Tag(VALID_TAG_MEETING));
        assertParseSuccess(parser, TAG_DESC_APPOINTMENT + TAG_DESC_MEETING,
                new AddEventTagCommand(expectedTags));
    }

    @Test
    public void parse_noTagValuesPresent_fail() {
        // zero tags
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagValue_failure() {

        // invalid tag
        assertParseFailure(parser, INVALID_TAG_DESC + VALID_TAG_APPOINTMENT, Tag.MESSAGE_TAG_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TAG_DESC_APPOINTMENT + TAG_DESC_MEETING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventTagCommand.MESSAGE_USAGE));
    }
}
