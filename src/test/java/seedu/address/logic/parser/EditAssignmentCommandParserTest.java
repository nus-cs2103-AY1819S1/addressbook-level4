package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_ASSIGNMENT_DESC_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_ASSIGNMENT_DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_ASSIGNMENT_DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.person.Name;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE);

    private EditAssignmentCommandParser parser = new EditAssignmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PROJECT_OASIS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditAssignmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_ASSIGNMENT_DESC_OASIS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_ASSIGNMENT_DESC_OASIS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ASSIGNMENT_NAME_DESC,
                ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS); // invalid assignment name
        assertParseFailure(parser, "1" + INVALID_ASSIGNMENT_AUTHOR_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS); // invalid name

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ASSIGNMENT_NAME_DESC + INVALID_ASSIGNMENT_AUTHOR_DESC
                        + VALID_SALARY_AMY + VALID_PHONE_AMY, ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + NAME_ASSIGNMENT_DESC_OASIS + AUTHOR_ASSIGNMENT_DESC_OASIS
                + ASSIGNMENT_DESC_OASIS;

        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withAssignmentName(VALID_PROJECT_OASIS)
                .withAuthor(VALID_NAME_AMY)
                .withDescription(VALID_DESCRIPTION_OASIS)
                .build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + AUTHOR_ASSIGNMENT_DESC_OASIS;

        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withAuthor(VALID_NAME_AMY).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // assignment name
        Index targetIndex = INDEX_SECOND_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + NAME_ASSIGNMENT_DESC_OASIS;
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_PROJECT_OASIS).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // author
        userInput = targetIndex.getOneBased() + AUTHOR_ASSIGNMENT_DESC_OASIS;
        descriptor = new EditAssignmentDescriptorBuilder().withAuthor(VALID_NAME_AMY).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + ASSIGNMENT_DESC_OASIS;
        descriptor = new EditAssignmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_OASIS).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + AUTHOR_ASSIGNMENT_DESC_OASIS + ASSIGNMENT_DESC_OASIS
                + AUTHOR_ASSIGNMENT_DESC_OASIS + ASSIGNMENT_DESC_OASIS
                + AUTHOR_ASSIGNMENT_DESC_FALCON + ASSIGNMENT_DESC_FALCON;

        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withAuthor(VALID_NAME_BOB)
                .withDescription(VALID_DESCRIPTION_FALCON).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ASSIGNMENT;
        String userInput = targetIndex.getOneBased() + INVALID_ASSIGNMENT_AUTHOR_DESC + AUTHOR_ASSIGNMENT_DESC_OASIS;
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withAuthor(VALID_NAME_AMY).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_ASSIGNMENT_AUTHOR_DESC
                + ASSIGNMENT_DESC_OASIS + AUTHOR_ASSIGNMENT_DESC_OASIS;
        descriptor = new EditAssignmentDescriptorBuilder().withAuthor(VALID_NAME_AMY)
                .withDescription(VALID_DESCRIPTION_OASIS).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
