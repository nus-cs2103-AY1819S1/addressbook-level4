package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_SAMPLE_1;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_SAMPLE_2;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_1;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemarkCommand;

public class RemarkCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, SAMPLE_REMARK_1);
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // empty remark
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + REMARK_DESC_EMPTY,
                new RemarkCommand(INDEX_FIRST_PERSON, SAMPLE_REMARK_EMPTY));

        // no empty fields
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + REMARK_DESC_SAMPLE_1,
                expectedCommand);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // empty index
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, "-5" + REMARK_DESC_SAMPLE_1, MESSAGE_INVALID_FORMAT);

        //zero index
        assertParseFailure(parser, "0" + REMARK_DESC_SAMPLE_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "5 random string" + REMARK_DESC_SAMPLE_1, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        // remark is repeated twice -> second remark is taken
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                + REMARK_DESC_SAMPLE_2 + REMARK_DESC_SAMPLE_1, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // missing index
        assertParseFailure(parser, REMARK_DESC_SAMPLE_1, MESSAGE_INVALID_FORMAT);

        // missing remark
        assertParseFailure(parser, "" + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_FORMAT);
    }
}
