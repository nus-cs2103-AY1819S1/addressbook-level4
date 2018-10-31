package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWishes.AMY;
import static seedu.address.testutil.TypicalWishes.BOB;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Date;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;
import seedu.address.testutil.WishBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Wish expectedWish = new WishBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2
                + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWish));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2
                + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWish));

        // multiple prices - last price accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_AMY + PRICE_DESC_BOB + DATE_DESC_2
                + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWish));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_1 + DATE_DESC_2
                + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWish));

        // multiple urls - last url accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2 + URL_DESC_AMY
                + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWish));

        // multiple tags - all accepted
        Wish expectedWishMultipleTags = new WishBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2 + URL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedWishMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Wish expectedWish = new WishBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PRICE_DESC_AMY + DATE_DESC_1 + URL_DESC_AMY,
                new AddCommand(expectedWish));
    }

    @Test
    public void parse_additionalFields_failure() {
        // both DATE and AGE used, invalid
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_1 + AGE_DESC_1
                + URL_DESC_BOB, Messages.MESSAGE_DOUBLE_DATE_FORMAT);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PRICE_DESC_BOB + DATE_DESC_2 + URL_DESC_BOB,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PRICE_BOB + DATE_DESC_2 + URL_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + VALID_DATE_2 + URL_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PRICE_BOB + VALID_DATE_2 + VALID_URL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_BOB + DATE_DESC_2 + URL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PRICE_DESC + DATE_DESC_2 + URL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + INVALID_DATE_DESC + URL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid url
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2 + INVALID_URL_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Url.MESSAGE_URL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2 + URL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_BOB + DATE_DESC_2 + INVALID_URL_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PRICE_DESC_BOB + DATE_DESC_2
                + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
