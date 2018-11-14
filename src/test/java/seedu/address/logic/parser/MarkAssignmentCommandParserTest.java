package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENTID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARK_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.model.common.Mark;

public class MarkAssignmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAssignmentCommand.MESSAGE_USAGE);

    private MarkAssignmentCommandParser parser = new MarkAssignmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no person index specified
        assertParseFailure(parser, VALID_ASSIGNMENTID_DESC + VALID_MARK_DESC, MESSAGE_INVALID_FORMAT);

        // no assignment index specified
        assertParseFailure(parser, "1" + VALID_MARK_DESC, MESSAGE_INVALID_FORMAT);

        // no assignment mark specified
        assertParseFailure(parser, "1" + VALID_ASSIGNMENTID_DESC, MESSAGE_INVALID_FORMAT);

        // no person index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_ASSIGNMENTID_DESC + VALID_MARK_DESC, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + VALID_ASSIGNMENTID_DESC + VALID_MARK_DESC, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid mark
        assertParseFailure(parser, "1" + VALID_ASSIGNMENTID_DESC + INVALID_MARK_DESC, Mark.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allValidValue_success() {
        Index targetPersonIndex = INDEX_SECOND_PERSON;
        Index targetAssignmentIndex = Index.fromOneBased(Integer.valueOf(VALID_ASSIGNMENTID));
        Mark targetMark = new Mark(VALID_MARK);
        String userInput = targetPersonIndex.getOneBased() + VALID_ASSIGNMENTID_DESC + VALID_MARK_DESC;

        MarkAssignmentCommand expectedCommand =
                new MarkAssignmentCommand(targetPersonIndex, targetAssignmentIndex, targetMark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
