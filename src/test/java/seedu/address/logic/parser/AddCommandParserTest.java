package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DRIVER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRIVER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalVolunteers.AMY;
import static seedu.address.testutil.TypicalVolunteers.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Volunteer expectedVolunteer = new VolunteerBuilder(BOB).withTags(VALID_TAG_STUDENT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_STUDENT,
                new AddCommand(expectedVolunteer));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_STUDENT,
                new AddCommand(expectedVolunteer));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_AMY + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT, new AddCommand(expectedVolunteer));

        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_AMY
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT, new AddCommand(expectedVolunteer));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT, new AddCommand(expectedVolunteer));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT, new AddCommand(expectedVolunteer));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT, new AddCommand(expectedVolunteer));

        // multiple tags - all accepted
        Volunteer expectedVolunteerMultipleTags = new VolunteerBuilder(BOB)
                .withTags(VALID_TAG_STUDENT, VALID_TAG_DRIVER).build();
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT,
                new AddCommand(expectedVolunteerMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Volunteer expectedVolunteer = new VolunteerBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY + BIRTHDAY_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedVolunteer));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_GENDER_BOB + BIRTHDAY_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing birthday prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + VALID_BIRTHDAY_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_GENDER_BOB + VALID_BIRTHDAY_BOB
                + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_VOLUNTEER_NAME_DESC + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_GENDER_DESC + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT, Gender.MESSAGE_GENDER_CONSTRAINTS);

        // invalid birthday
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + INVALID_BIRTHDAY_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_DRIVER + TAG_DESC_STUDENT, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_STUDENT, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_VOLUNTEER_NAME_DESC + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + GENDER_DESC_BOB
                        + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_DRIVER + TAG_DESC_STUDENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
