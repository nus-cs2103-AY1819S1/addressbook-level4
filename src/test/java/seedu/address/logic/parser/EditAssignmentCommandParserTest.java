package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENTNAME_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENTNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAXMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEIGHT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAXMARK_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.MAXMARK_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENTNAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAXMARK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAXMARK_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_B;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ASSIGNMENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE);

    private EditAssignmentCommandParser parser = new EditAssignmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ASSIGNMENTNAME_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditAssignmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ASSIGNMENTNAME_DESC_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ASSIGNMENTNAME_DESC_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid assignment name
        assertParseFailure(parser, "1" + INVALID_ASSIGNMENTNAME_DESC, AssignmentName.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_WEIGHT_DESC, Weight.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_MAXMARK_DESC, Mark.MESSAGE_CONSTRAINTS); // invalid address

        // invalid weight followed by valid deadline
        assertParseFailure(parser, "1" + INVALID_WEIGHT_DESC + DEADLINE_DESC_A, Weight.MESSAGE_CONSTRAINTS);

        // valid weight followed by invalid weight. The test case for invalid weight followed by valid weight
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + WEIGHT_DESC_B + INVALID_WEIGHT_DESC, Weight.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ASSIGNMENTNAME_DESC + INVALID_DEADLINE_DESC + VALID_MAXMARK_A
                        + VALID_WEIGHT_A, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + WEIGHT_DESC_B + DEADLINE_DESC_A + MAXMARK_DESC_A
                + ASSIGNMENTNAME_DESC_A;

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withName(VALID_ASSIGNMENTNAME_A)
                .withWeight(VALID_WEIGHT_B).withDeadline(VALID_DEADLINE_A).withMaxMark(VALID_MAXMARK_A).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + WEIGHT_DESC_B + DEADLINE_DESC_A;

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withWeight(VALID_WEIGHT_B)
                .withDeadline(VALID_DEADLINE_A).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // assignment name
        Index targetIndex = INDEX_THIRD_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + ASSIGNMENTNAME_DESC_A;
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withName(VALID_ASSIGNMENTNAME_A).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // weight
        userInput = targetIndex.getOneBased() + WEIGHT_DESC_A;
        descriptor = new EditAssignmentDescriptorBuilder().withWeight(VALID_WEIGHT_A).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_A;
        descriptor = new EditAssignmentDescriptorBuilder().withDeadline(VALID_DEADLINE_A).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // max mark
        userInput = targetIndex.getOneBased() + MAXMARK_DESC_A;
        descriptor = new EditAssignmentDescriptorBuilder().withMaxMark(VALID_MAXMARK_A).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + WEIGHT_DESC_A + MAXMARK_DESC_A + DEADLINE_DESC_A + WEIGHT_DESC_A
                + MAXMARK_DESC_A + DEADLINE_DESC_A + WEIGHT_DESC_B + MAXMARK_DESC_B + DEADLINE_DESC_B;

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withWeight(VALID_WEIGHT_B)
                .withDeadline(VALID_DEADLINE_B).withMaxMark(VALID_MAXMARK_B).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + INVALID_WEIGHT_DESC + WEIGHT_DESC_B;
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withWeight(VALID_WEIGHT_B).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_B + INVALID_WEIGHT_DESC + MAXMARK_DESC_B
                + WEIGHT_DESC_B;
        descriptor = new EditAssignmentDescriptorBuilder().withWeight(VALID_WEIGHT_B).withDeadline(VALID_DEADLINE_B)
                .withMaxMark(VALID_MAXMARK_B).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
