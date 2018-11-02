package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.FINDER_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.FINDER_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_FINDER_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_RED;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_FINDER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_RED;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.lostandfound.testutil.TypicalArticles.MOUSE;
import static seedu.lostandfound.testutil.TypicalArticles.POWERBANK;

import org.junit.Test;

import seedu.lostandfound.logic.commands.AddCommand;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;
import seedu.lostandfound.testutil.ArticleBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Article expectedArticle = new ArticleBuilder(MOUSE).withTags(VALID_TAG_BLUE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_BLUE, new AddCommand(expectedArticle));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_POWERBANK + NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_BLUE, new AddCommand(expectedArticle));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_MOUSE + PHONE_DESC_POWERBANK + PHONE_DESC_MOUSE
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE
                + TAG_DESC_BLUE, new AddCommand(expectedArticle));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_POWERBANK
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE
                + TAG_DESC_BLUE, new AddCommand(expectedArticle));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_POWERBANK + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE
                + TAG_DESC_BLUE, new AddCommand(expectedArticle));

        // multiple finders - last finder accepted
        assertParseSuccess(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_POWERBANK + FINDER_DESC_MOUSE
                + TAG_DESC_BLUE, new AddCommand(expectedArticle));

        // multiple tags - all accepted
        Article expectedArticleMultipleTags = new ArticleBuilder(MOUSE).withTags(VALID_TAG_RED, VALID_TAG_BLUE)
                .build();
        assertParseSuccess(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_RED
                + TAG_DESC_BLUE, new AddCommand(expectedArticleMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Article expectedArticle = new ArticleBuilder(POWERBANK).withTags().build();
        assertParseSuccess(parser, NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                        + DESCRIPTION_DESC_POWERBANK + FINDER_DESC_POWERBANK, new AddCommand(expectedArticle));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                        + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_MOUSE + VALID_PHONE_MOUSE + EMAIL_DESC_MOUSE
                        + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + VALID_EMAIL_MOUSE
                        + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                        + VALID_DESCRIPTION_MOUSE + FINDER_DESC_MOUSE, expectedMessage);

        // missing finder prefix
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + VALID_FINDER_MOUSE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_MOUSE + VALID_PHONE_MOUSE + VALID_EMAIL_MOUSE
                        + VALID_DESCRIPTION_MOUSE + VALID_FINDER_MOUSE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_RED + TAG_DESC_BLUE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_MOUSE + INVALID_PHONE_DESC + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_RED + TAG_DESC_BLUE, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + INVALID_EMAIL_DESC
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_RED + TAG_DESC_BLUE, Email.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + INVALID_DESCRIPTION_DESC + FINDER_DESC_MOUSE + TAG_DESC_RED
                + TAG_DESC_BLUE, Description.MESSAGE_CONSTRAINTS);

        // invalid finder
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + INVALID_DESCRIPTION_DESC + INVALID_FINDER_DESC + TAG_DESC_RED
                + TAG_DESC_BLUE, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + INVALID_TAG_DESC
                + VALID_TAG_RED, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                        + INVALID_DESCRIPTION_DESC + FINDER_DESC_MOUSE, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_MOUSE + PHONE_DESC_MOUSE + EMAIL_DESC_MOUSE
                + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_RED + TAG_DESC_BLUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
