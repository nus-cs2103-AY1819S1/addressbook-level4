package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_OWNER_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.OWNER_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.OWNER_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_OWNER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_OWNER_POWERBANK;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_THIRD_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.ResolveCommand;
import seedu.lostandfound.model.article.Name;

public class ResolveCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResolveCommand.MESSAGE_USAGE);

    private ResolveCommandParser parser = new ResolveCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_POWERBANK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ResolveCommand.MESSAGE_NOT_RESOLVED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_POWERBANK, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_POWERBANK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_OWNER_DESC, Name.MESSAGE_CONSTRAINTS); // invalid finder

        // valid owner followed by invalid owner. The test case for invalid owner followed by valid owner
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + OWNER_DESC_MOUSE + INVALID_OWNER_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_fieldSpecified_success() {
        // owner
        Index targetIndex = INDEX_THIRD_ARTICLE;
        String userInput = targetIndex.getOneBased() + OWNER_DESC_POWERBANK;
        Name owner = new Name(VALID_OWNER_POWERBANK);
        ResolveCommand expectedCommand = new ResolveCommand(targetIndex, owner);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedField_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ARTICLE;
        String userInput = targetIndex.getOneBased() + OWNER_DESC_POWERBANK + OWNER_DESC_POWERBANK
                + OWNER_DESC_MOUSE;

        Name owner = new Name(VALID_OWNER_MOUSE);
        ResolveCommand expectedCommand = new ResolveCommand(targetIndex, owner);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ARTICLE;
        String userInput = targetIndex.getOneBased() + INVALID_OWNER_DESC + OWNER_DESC_MOUSE;
        Name owner = new Name(VALID_OWNER_MOUSE);
        ResolveCommand expectedCommand = new ResolveCommand(targetIndex, owner);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
