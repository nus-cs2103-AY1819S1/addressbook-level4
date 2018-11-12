package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PrivacyCommand;
import seedu.address.logic.commands.PrivacyCommand.FieldsToChange;
import seedu.address.testutil.FieldsToChangeBuilder;

public class PrivacyCommandParserTest {

    private static final String PRIVACY_CONSTRAINTS =
            "Privacy option should be only Y or N!";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrivacyCommand.MESSAGE_USAGE);

    private PrivacyCommandParser parser = new PrivacyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", PrivacyCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        //negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        //zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        //invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 something", MESSAGE_INVALID_FORMAT);

        //invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + " a/g", PRIVACY_CONSTRAINTS);

        // invalid phone privacy followed by valid email privacy
        assertParseFailure(parser, "1" + " p/s e/y", PRIVACY_CONSTRAINTS);

        // valid phone privacy followed by invalid phone privacy
        assertParseFailure(parser, "1" + " p/y p/g", PRIVACY_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value captured
        assertParseFailure(parser, "1" + " p/g a/g e/g", PRIVACY_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " p/y e/y a/y";
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withAllPrivate().build();
        PrivacyCommand expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " p/y a/y";
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withPrivateAddress().withPrivatePhone()
                .build();
        PrivacyCommand expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // phone privacy
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " p/y";
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withPrivatePhone().build();
        PrivacyCommand expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);
        assertParseSuccess(parser, userInput, expectedCommand);

        //address privacy
        userInput = targetIndex.getOneBased() + " a/y";
        fieldsToChange = new FieldsToChangeBuilder().withPrivateAddress().build();
        expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);
        assertParseSuccess(parser, userInput, expectedCommand);

        //email privacy
        userInput = targetIndex.getOneBased() + " e/y";
        fieldsToChange = new FieldsToChangeBuilder().withPrivateEmail().build();
        expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " p/n a/n e/n p/y a/y e/y";
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withAllPrivate().build();
        PrivacyCommand expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parser_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " p/g p/y";
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withPrivatePhone().build();
        PrivacyCommand expectedCommand = new PrivacyCommand(targetIndex, fieldsToChange);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
