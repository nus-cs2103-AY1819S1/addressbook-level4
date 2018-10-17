package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_FLY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_ABILITY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_FLOATING;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_FLY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_ABILITY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_FLOATING;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_FIRST_WORD;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_SECOND_WORD;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_THIRD_WORD;

import org.junit.Test;

import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.commands.EditCommand;
import seedu.learnvocabulary.logic.commands.EditCommand.EditWordDescriptor;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.testutil.EditWordDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_FLY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_FLY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_FLY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Word} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FLOATING + TAG_DESC_ABILITY + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FLOATING + TAG_EMPTY + TAG_DESC_ABILITY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FLOATING + TAG_DESC_ABILITY, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_WORD;
        String userInput = targetIndex.getOneBased() + TAG_DESC_ABILITY
                + NAME_DESC_FLY + TAG_DESC_FLOATING;

        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withName(VALID_NAME_FLY)
                .withTags(VALID_TAG_ABILITY, VALID_TAG_FLOATING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*
    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_WORD;
        String userInput = targetIndex.getOneBased();

        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    */

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_WORD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_FLY;
        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withName(VALID_NAME_FLY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FLOATING;
        descriptor = new EditWordDescriptorBuilder().withTags(VALID_TAG_FLOATING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_WORD;
        String userInput = targetIndex.getOneBased()
                + TAG_DESC_FLOATING + TAG_DESC_FLOATING
                + TAG_DESC_ABILITY;

        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withTags(VALID_TAG_FLOATING, VALID_TAG_ABILITY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    //@Test
    //public void parse_invalidValueFollowedByValidValue_success() {
    //    // no other valid values specified
    //    Index targetIndex = INDEX_FIRST_WORD;
    //    String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + DESC_LEVITATE;
    //    EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withName(VALID_NAME_FLY).build();
    //    EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);
    //
    //    // other valid values specified
    //    userInput = targetIndex.getOneBased() + VALID_NAME_LEVITATE + VALID_MEANING + INVALID_TAG_DESC;
    //    descriptor = new EditWordDescriptorBuilder().withName(VALID_NAME_FLY).withMeaning(VALID_MEANING)
    //            .withTags(VALID_TAG_FLOATING).build();
    //    expectedCommand = new EditCommand(targetIndex, descriptor);
    //    assertParseSuccess(parser, userInput, expectedCommand);
    //}

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_WORD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
