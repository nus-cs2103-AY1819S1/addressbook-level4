package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_RED;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_RED;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_SECOND_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_THIRD_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.EditCommand;
import seedu.lostandfound.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;
import seedu.lostandfound.testutil.EditArticleDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_POWERBANK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1 q/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_POWERBANK,
                Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_MOUSE + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Article} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_BLUE + TAG_DESC_RED + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_BLUE + TAG_EMPTY + TAG_DESC_RED, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_BLUE + TAG_DESC_RED, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_DESCRIPTION_POWERBANK
                        + VALID_PHONE_POWERBANK, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ARTICLE;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_MOUSE + TAG_DESC_RED
                + EMAIL_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK + NAME_DESC_POWERBANK + TAG_DESC_BLUE;

        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withName(VALID_NAME_POWERBANK)
                .withPhone(VALID_PHONE_MOUSE).withEmail(VALID_EMAIL_POWERBANK)
                .withDescription(VALID_DESCRIPTION_POWERBANK).withTags(VALID_TAG_BLUE, VALID_TAG_RED).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ARTICLE;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_MOUSE + EMAIL_DESC_POWERBANK;

        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withPhone(VALID_PHONE_MOUSE)
                .withEmail(VALID_EMAIL_POWERBANK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ARTICLE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_POWERBANK;
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withName(VALID_NAME_POWERBANK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_POWERBANK;
        descriptor = new EditArticleDescriptorBuilder().withPhone(VALID_PHONE_POWERBANK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_POWERBANK;
        descriptor = new EditArticleDescriptorBuilder().withEmail(VALID_EMAIL_POWERBANK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_POWERBANK;
        descriptor = new EditArticleDescriptorBuilder().withDescription(VALID_DESCRIPTION_POWERBANK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_RED;
        descriptor = new EditArticleDescriptorBuilder().withTags(VALID_TAG_RED).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ARTICLE;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK
                + EMAIL_DESC_POWERBANK + TAG_DESC_BLUE + PHONE_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK
                + EMAIL_DESC_POWERBANK + TAG_DESC_BLUE + PHONE_DESC_MOUSE + DESCRIPTION_DESC_MOUSE
                + EMAIL_DESC_MOUSE + TAG_DESC_RED;

        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withPhone(VALID_PHONE_MOUSE)
                .withEmail(VALID_EMAIL_MOUSE).withDescription(VALID_DESCRIPTION_MOUSE)
                .withTags(VALID_TAG_RED, VALID_TAG_BLUE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ARTICLE;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_MOUSE;
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withPhone(VALID_PHONE_MOUSE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_MOUSE + INVALID_PHONE_DESC + DESCRIPTION_DESC_MOUSE
                + PHONE_DESC_MOUSE;
        descriptor = new EditArticleDescriptorBuilder().withPhone(VALID_PHONE_MOUSE).withEmail(VALID_EMAIL_MOUSE)
                .withDescription(VALID_DESCRIPTION_MOUSE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ARTICLE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
