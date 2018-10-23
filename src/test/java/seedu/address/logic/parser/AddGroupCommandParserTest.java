package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_DESC_GROUP_0;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_DESC_GROUP_2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_GROUP_0;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.GROUP_0;

import org.junit.Test;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.shared.Title;
import seedu.address.testutil.GroupBuilder;

// @@author Derek-Hardy
public class AddGroupCommandParserTest {

    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withTitle(GROUP_0.getTitle().fullTitle).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + VALID_GROUP_TITLE_DESC_GROUP_0, new AddGroupCommand(expectedGroup));

        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_GROUP_TITLE_DESC_GROUP_2101
                + VALID_GROUP_TITLE_DESC_GROUP_0, new AddGroupCommand(expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, VALID_GROUP_TITLE_GROUP_0, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_GROUP_TITLE_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_GROUP_TITLE_GROUP_0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
    }


}
