package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.jxmusic.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.jxmusic.logic.commands.CommandTestUtil.NAME_DESC_ANIME;
import static seedu.jxmusic.logic.commands.CommandTestUtil.NAME_DESC_METAL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_DESC_ALIEZ;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_DESC_EXISTENCE;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_NAME_METAL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_EXISTENCE;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_ALIEZ;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylists.AMY;
import static seedu.jxmusic.testutil.TypicalPlaylists.BOB;

import org.junit.Test;

import seedu.jxmusic.logic.commands.AddCommand;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.tag.Tag;
import seedu.jxmusic.testutil.PlaylistBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PlaylistBuilder(BOB).withTags(VALID_TRACK_EXISTENCE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TRACK_DESC_ALIEZ, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_ANIME + NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TRACK_DESC_ALIEZ, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_METAL + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TRACK_DESC_ALIEZ, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TRACK_DESC_ALIEZ, new AddCommand(expectedPerson));

        // multiple addresses - last jxmusic accepted
        assertParseSuccess(parser, NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TRACK_DESC_ALIEZ, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PlaylistBuilder(BOB).withTags(VALID_TRACK_EXISTENCE, VALID_TRACK_ALIEZ)
                .build();
        assertParseSuccess(parser, NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TRACK_DESC_EXISTENCE + TRACK_DESC_ALIEZ, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PlaylistBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_ANIME + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_METAL + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_METAL + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing jxmusic prefix
        assertParseFailure(parser, NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_METAL + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TRACK_DESC_EXISTENCE + TRACK_DESC_ALIEZ, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_METAL + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TRACK_DESC_EXISTENCE + TRACK_DESC_ALIEZ, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_METAL + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TRACK_DESC_EXISTENCE + TRACK_DESC_ALIEZ, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid jxmusic
        assertParseFailure(parser, NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TRACK_DESC_EXISTENCE + TRACK_DESC_ALIEZ, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TRACK_EXISTENCE, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_METAL + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TRACK_DESC_EXISTENCE + TRACK_DESC_ALIEZ,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
