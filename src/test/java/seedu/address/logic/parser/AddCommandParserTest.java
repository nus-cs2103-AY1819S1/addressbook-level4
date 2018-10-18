package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_C;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATEMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SOLUTION_DESC_C;
import static seedu.address.logic.commands.CommandTestUtil.SOLUTION_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.STATEMENT_DESC_C;
import static seedu.address.logic.commands.CommandTestUtil.STATEMENT_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SYNTAX;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_UI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SYNTAX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UI;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.Issue;
import seedu.address.model.issue.Description;
import seedu.address.model.issue.IssueStatement;
import seedu.address.model.issue.Tag;
import seedu.address.testutil.IssueBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    @Ignore
    public void parse_allFieldsPresent_success() {
        Issue expectedIssue = new IssueBuilder(BOB).withTags(VALID_TAG_UI).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STATEMENT_DESC_C + DESCRIPTION_DESC_C
                + SOLUTION_DESC_C + CommandTestUtil.TAG_DESC_UI, new AddCommand(expectedIssue));

        // multiple names - last name accepted
        assertParseSuccess(parser, STATEMENT_DESC_JAVA + STATEMENT_DESC_C + DESCRIPTION_DESC_C
                + SOLUTION_DESC_C + CommandTestUtil.TAG_DESC_UI, new AddCommand(expectedIssue));

        // multiple descriptionss - last descriptions accepted
        assertParseSuccess(parser, STATEMENT_DESC_C + DESCRIPTION_DESC_JAVA + DESCRIPTION_DESC_C
                + SOLUTION_DESC_C + CommandTestUtil.TAG_DESC_UI, new AddCommand(expectedIssue));

        // multiple solutions - all accepted
        Issue expectedIssueMultipleSolutions = new IssueBuilder(BOB)
                .withSolutions(VALID_SOLUTION_JAVA, VALID_SOLUTION_C).build();
        assertParseSuccess(parser, STATEMENT_DESC_C + DESCRIPTION_DESC_C + SOLUTION_DESC_JAVA
                + SOLUTION_DESC_C + CommandTestUtil.TAG_DESC_UI, new AddCommand(expectedIssueMultipleSolutions));

        // multiple tags - all accepted
        Issue expectedIssueMultipleTags = new IssueBuilder(BOB).withTags(VALID_TAG_SYNTAX, VALID_TAG_UI)
                .build();
        assertParseSuccess(parser, STATEMENT_DESC_C + DESCRIPTION_DESC_C + SOLUTION_DESC_C
                + TAG_DESC_SYNTAX + CommandTestUtil.TAG_DESC_UI, new AddCommand(expectedIssueMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Issue expectedIssue = new IssueBuilder(AMY).withTags().withSolutions().build();
        assertParseSuccess(parser, STATEMENT_DESC_JAVA + DESCRIPTION_DESC_JAVA,
                new AddCommand(expectedIssue));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing statement prefix
        assertParseFailure(parser, VALID_STATEMENT_C + DESCRIPTION_DESC_C + SOLUTION_DESC_C,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, STATEMENT_DESC_C + VALID_DESCRIPTION_C + SOLUTION_DESC_C,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STATEMENT_C + VALID_DESCRIPTION_C + VALID_SOLUTION_C,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_STATEMENT_DESC + DESCRIPTION_DESC_C
                + TAG_DESC_UI + CommandTestUtil.TAG_DESC_UI, IssueStatement.MESSAGE_ISSUE_STATEMENT_CONSTRAINTS);

        // invalid descriptions
        assertParseFailure(parser, STATEMENT_DESC_C + INVALID_DESCRIPTION_DESC
                + TAG_DESC_UI + CommandTestUtil.TAG_DESC_UI, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, STATEMENT_DESC_C + DESCRIPTION_DESC_C
                + INVALID_TAG_DESC + VALID_TAG_SYNTAX, Tag.MESSAGE_TAG_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STATEMENT_DESC_C + DESCRIPTION_DESC_C
                + TAG_DESC_UI + CommandTestUtil.TAG_DESC_UI,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
