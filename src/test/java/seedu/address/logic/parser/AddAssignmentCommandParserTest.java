package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_ASSIGNMENT_DESC_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_ASSIGNMENT_DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_ASSIGNMENT_DESC_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.NAME_ASSIGNMENT_DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_OASIS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAssignment.OASIS;
import org.junit.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.person.Name;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandParserTest {
    private AddAssignmentParser parser = new AddAssignmentParser();

    //@Test

    //public void parse_allFieldsPresent_success() {
        //Assignment expectedAssignment = new AssignmentBuilder(OASIS).build();

        // whitespace only preamble
        /*assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_ASSIGNMENT_DESC_OASIS + AUTHOR_ASSIGNMENT_DESC_OASIS
                + ASSIGNMENT_DESC_OASIS, new AddAssignmentCommand(expectedAssignment));*/

        // multiple assignment names - last name accepted
        /*assertParseSuccess(parser, NAME_ASSIGNMENT_DESC_OASIS + NAME_ASSIGNMENT_DESC_FALCON
                + AUTHOR_ASSIGNMENT_DESC_FALCON + ASSIGNMENT_DESC_FALCON, new AddAssignmentCommand(expectedAssignment));

        // multiple phones - last author accepted
        assertParseSuccess(parser, NAME_ASSIGNMENT_DESC_OASIS + AUTHOR_ASSIGNMENT_DESC_FALCON
                + AUTHOR_ASSIGNMENT_DESC_OASIS + ASSIGNMENT_DESC_OASIS, new AddAssignmentCommand(expectedAssignment));
    }*/

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // missing project name prefix
        assertParseFailure(parser, VALID_PROJECT_OASIS + AUTHOR_ASSIGNMENT_DESC_OASIS
                        + ASSIGNMENT_DESC_OASIS,
                expectedMessage);

        // missing author prefix
        assertParseFailure(parser, ASSIGNMENT_DESC_OASIS + VALID_NAME_AMY + ASSIGNMENT_DESC_OASIS,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, ASSIGNMENT_DESC_OASIS + AUTHOR_ASSIGNMENT_DESC_OASIS
                        + VALID_DESCRIPTION_OASIS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PROJECT_OASIS + VALID_NAME_AMY + VALID_DESCRIPTION_OASIS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid project name
        assertParseFailure(parser, INVALID_ASSIGNMENT_NAME_DESC + AUTHOR_ASSIGNMENT_DESC_OASIS
                        + ASSIGNMENT_DESC_OASIS,
                ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS);

        // invalid author
        assertParseFailure(parser, NAME_ASSIGNMENT_DESC_OASIS + INVALID_ASSIGNMENT_AUTHOR_DESC + ASSIGNMENT_DESC_OASIS,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ASSIGNMENT_NAME_DESC + INVALID_ASSIGNMENT_AUTHOR_DESC
                + ASSIGNMENT_DESC_OASIS,
                ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS);

        // non-empty preamble
        /*assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                        + PROJECT_DESC_FALCON + PROJECT_DESC_OASIS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));*/
    }
}
