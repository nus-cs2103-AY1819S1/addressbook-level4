package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENTNAME_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENTNAME_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENTNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAXMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEIGHT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAXMARK_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.MAXMARK_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENTNAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAXMARK_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_B;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_B;

import org.junit.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder(ASSIGNMENT_B).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B + DEADLINE_DESC_B
                + MAXMARK_DESC_B, new AddAssignmentCommand(expectedAssignment));

        // multiple names - last name accepted
        assertParseSuccess(parser, ASSIGNMENTNAME_DESC_A + ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B + DEADLINE_DESC_B
                + MAXMARK_DESC_B, new AddAssignmentCommand(expectedAssignment));

        // multiple weight - last weight accepted
        assertParseSuccess(parser, ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_A + WEIGHT_DESC_B + DEADLINE_DESC_B
                + MAXMARK_DESC_B, new AddAssignmentCommand(expectedAssignment));

        // multiple deadline - last deadline accepted
        assertParseSuccess(parser, ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B + DEADLINE_DESC_A + DEADLINE_DESC_B
                + MAXMARK_DESC_B, new AddAssignmentCommand(expectedAssignment));

        // multiple maxmark - last maxmark accepted
        assertParseSuccess(parser, ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B + DEADLINE_DESC_B + MAXMARK_DESC_A
                + MAXMARK_DESC_B, new AddAssignmentCommand(expectedAssignment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_ASSIGNMENTNAME_B + WEIGHT_DESC_B + DEADLINE_DESC_B + MAXMARK_DESC_B,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ASSIGNMENTNAME_B + VALID_WEIGHT_B + VALID_DEADLINE_B + VALID_MAXMARK_B,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid assignmentname
        assertParseFailure(parser, INVALID_ASSIGNMENTNAME_DESC + WEIGHT_DESC_B + DEADLINE_DESC_B + MAXMARK_DESC_B,
                AssignmentName.MESSAGE_CONSTRAINTS);

        // invalid weight
        assertParseFailure(parser, ASSIGNMENTNAME_DESC_B + INVALID_WEIGHT_DESC + DEADLINE_DESC_B + MAXMARK_DESC_B,
                Weight.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B + INVALID_DEADLINE_DESC + MAXMARK_DESC_B,
                Deadline.MESSAGE_CONSTRAINTS);

        // invalid maxmark
        assertParseFailure(parser, ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B + DEADLINE_DESC_B + INVALID_MAXMARK_DESC,
                Mark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, ASSIGNMENTNAME_DESC_B + INVALID_WEIGHT_DESC + DEADLINE_DESC_B + INVALID_MAXMARK_DESC,
                Weight.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ASSIGNMENTNAME_DESC_B + WEIGHT_DESC_B
                        + DEADLINE_DESC_B + MAXMARK_DESC_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
    }
}
