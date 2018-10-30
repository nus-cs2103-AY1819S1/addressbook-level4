package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVEDATES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVEDESCIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDATES_DESC_ALICE_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDATES_DESC_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDESCIPTION_DESC_ALICE_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDESCIPTION_DESC_BENSON_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDESCIPTION_DESC_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVEDATE_STRING_BOB_LEAVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalLeaveApplications.ALICE_LEAVE;

import org.junit.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.LeaveApplyCommand;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.testutil.LeaveApplicationBuilder;

public class LeaveApplyCommandParserTest {
    private LeaveApplyCommandParser parser = new LeaveApplyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        LeaveApplication expectedLeaveApplication = new LeaveApplicationBuilder(ALICE_LEAVE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LEAVEDESCIPTION_DESC_ALICE_LEAVE
                + LEAVEDATES_DESC_ALICE_LEAVE, new LeaveApplyCommand(expectedLeaveApplication));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, LEAVEDESCIPTION_DESC_BENSON_LEAVE + LEAVEDESCIPTION_DESC_ALICE_LEAVE
                + LEAVEDATES_DESC_ALICE_LEAVE, new LeaveApplyCommand(expectedLeaveApplication));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveApplyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_DESCRIPTION_BOB_LEAVE + LEAVEDATES_DESC_BOB_LEAVE,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, LEAVEDESCIPTION_DESC_BOB_LEAVE + VALID_LEAVEDATE_STRING_BOB_LEAVE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION_BOB_LEAVE + VALID_LEAVEDATE_STRING_BOB_LEAVE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_LEAVEDESCIPTION_DESC + LEAVEDATES_DESC_BOB_LEAVE,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, LEAVEDESCIPTION_DESC_BOB_LEAVE + INVALID_LEAVEDATES_DESC,
                DateUtil.MESSAGE_DATE_CONSTRANTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_LEAVEDESCIPTION_DESC + INVALID_LEAVEDATES_DESC,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LEAVEDESCIPTION_DESC_BOB_LEAVE
                + LEAVEDATES_DESC_BOB_LEAVE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveApplyCommand.MESSAGE_USAGE));
    }
}
