package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.MEANING_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_FLY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_LEVITATE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_ABILITY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_FLOATING;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_MEANING;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_LEVITATE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_ABILITY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_FLOATING;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.learnvocabulary.testutil.TypicalWords.FLY;
import static seedu.learnvocabulary.testutil.TypicalWords.LEVITATE;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.AddCommand;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Word expectedWord = new WordBuilder(LEVITATE).withTags(VALID_TAG_FLOATING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_LEVITATE + MEANING_DESC
                + TAG_DESC_FLOATING, new AddCommand(expectedWord));

        // multiple tags - all accepted
        Word expectedWordMultipleTags = new WordBuilder(LEVITATE).withTags(VALID_TAG_FLOATING, VALID_TAG_ABILITY)
                .build();
        assertParseSuccess(parser, NAME_DESC_LEVITATE + MEANING_DESC
                + TAG_DESC_ABILITY + TAG_DESC_FLOATING, new AddCommand(expectedWordMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Word expectedWord = new WordBuilder(FLY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_FLY + MEANING_DESC,
                new AddCommand(expectedWord));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_LEVITATE + VALID_MEANING,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_LEVITATE + VALID_MEANING,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + MEANING_DESC
                + TAG_DESC_ABILITY + TAG_DESC_FLOATING, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_LEVITATE + MEANING_DESC
                + INVALID_TAG_DESC + VALID_TAG_FLOATING, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + MEANING_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_LEVITATE
                        + MEANING_DESC + TAG_DESC_ABILITY + TAG_DESC_FLOATING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // multiple names
        assertParseFailure(parser, NAME_DESC_FLY + NAME_DESC_LEVITATE + MEANING_DESC
                + TAG_DESC_FLOATING, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
