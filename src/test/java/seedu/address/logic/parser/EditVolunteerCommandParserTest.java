package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_VOLUNTEER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_VOLUNTEER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_VOLUNTEER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_VOLUNTEER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_VOLUNTEER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_VOLUNTEER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_VOLUNTEER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_VOLUNTEER_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_VOLUNTEER_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_TAG_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVolunteerCommand;
import seedu.address.logic.commands.EditVolunteerCommand.EditVolunteerDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.VolunteerAddress;
import seedu.address.model.volunteer.VolunteerEmail;
import seedu.address.model.volunteer.VolunteerName;
import seedu.address.model.volunteer.VolunteerPhone;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;

public class EditVolunteerCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVolunteerCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_VOLUNTEER_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditVolunteerCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_VOLUNTEER_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_VOLUNTEER_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_NAME_DESC,
                VolunteerName.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC,
                Gender.MESSAGE_GENDER_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_BIRTHDAY_DESC,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS); // invalid birthday
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_PHONE_DESC,
                VolunteerPhone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_EMAIL_DESC,
                VolunteerEmail.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_ADDRESS_DESC,
                VolunteerAddress.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_PHONE_DESC + EMAIL_VOLUNTEER_DESC_AMY,
                VolunteerPhone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_VOLUNTEER_DESC_BOB + INVALID_VOLUNTEER_PHONE_DESC,
                VolunteerPhone.MESSAGE_PHONE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_VOLUNTEER_DESC_FRIEND + TAG_VOLUNTEER_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_VOLUNTEER_DESC_FRIEND + TAG_EMPTY + TAG_VOLUNTEER_DESC_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_VOLUNTEER_DESC_FRIEND + TAG_VOLUNTEER_DESC_HUSBAND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_VOLUNTEER_NAME_DESC + INVALID_VOLUNTEER_EMAIL_DESC
                        + VALID_VOLUNTEER_ADDRESS_AMY + VALID_VOLUNTEER_PHONE_AMY + VALID_GENDER_AMY
                + VALID_BIRTHDAY_AMY, VolunteerName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_VOLUNTEER_DESC_BOB + TAG_VOLUNTEER_DESC_HUSBAND
                + EMAIL_VOLUNTEER_DESC_AMY + ADDRESS_VOLUNTEER_DESC_AMY + NAME_VOLUNTEER_DESC_AMY
                + GENDER_DESC_AMY + BIRTHDAY_DESC_AMY + TAG_VOLUNTEER_DESC_FRIEND;

        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withName(VALID_VOLUNTEER_NAME_AMY)
                .withPhone(VALID_VOLUNTEER_PHONE_BOB).withEmail(VALID_VOLUNTEER_EMAIL_AMY)
                .withAddress(VALID_VOLUNTEER_ADDRESS_AMY).withGender(VALID_GENDER_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withTags(VALID_VOLUNTEER_TAG_DRIVER, VALID_VOLUNTEER_TAG_FRIEND).build();
        EditVolunteerCommand expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_VOLUNTEER_DESC_BOB + EMAIL_VOLUNTEER_DESC_AMY;

        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withPhone(VALID_VOLUNTEER_PHONE_BOB)
                .withEmail(VALID_VOLUNTEER_EMAIL_AMY).build();
        EditVolunteerCommand expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_VOLUNTEER_DESC_AMY;
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder()
                .withName(VALID_VOLUNTEER_NAME_AMY).build();
        EditVolunteerCommand expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditVolunteerDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditVolunteerDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_VOLUNTEER_DESC_AMY;
        descriptor = new EditVolunteerDescriptorBuilder().withPhone(VALID_VOLUNTEER_PHONE_AMY).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_VOLUNTEER_DESC_AMY;
        descriptor = new EditVolunteerDescriptorBuilder().withEmail(VALID_VOLUNTEER_EMAIL_AMY).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_VOLUNTEER_DESC_AMY;
        descriptor = new EditVolunteerDescriptorBuilder().withAddress(VALID_VOLUNTEER_ADDRESS_AMY).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_VOLUNTEER_DESC_FRIEND;
        descriptor = new EditVolunteerDescriptorBuilder().withTags(VALID_VOLUNTEER_TAG_FRIEND).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_VOLUNTEER_DESC_AMY + ADDRESS_VOLUNTEER_DESC_AMY
                + EMAIL_VOLUNTEER_DESC_AMY + GENDER_DESC_AMY + BIRTHDAY_DESC_AMY + TAG_VOLUNTEER_DESC_FRIEND
                + PHONE_VOLUNTEER_DESC_AMY + ADDRESS_VOLUNTEER_DESC_AMY + EMAIL_VOLUNTEER_DESC_AMY + GENDER_DESC_AMY
                + BIRTHDAY_DESC_AMY + TAG_VOLUNTEER_DESC_FRIEND + PHONE_VOLUNTEER_DESC_BOB + ADDRESS_VOLUNTEER_DESC_BOB
                + EMAIL_VOLUNTEER_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB + TAG_VOLUNTEER_DESC_HUSBAND;

        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withPhone(VALID_VOLUNTEER_PHONE_BOB)
                .withEmail(VALID_VOLUNTEER_EMAIL_BOB).withAddress(VALID_VOLUNTEER_ADDRESS_BOB)
                .withGender(VALID_GENDER_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withTags(VALID_VOLUNTEER_TAG_FRIEND, VALID_VOLUNTEER_TAG_DRIVER).build();
        EditVolunteerCommand expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_VOLUNTEER_PHONE_DESC + PHONE_VOLUNTEER_DESC_BOB;
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder()
                .withPhone(VALID_VOLUNTEER_PHONE_BOB).build();
        EditVolunteerCommand expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_VOLUNTEER_DESC_BOB + INVALID_VOLUNTEER_PHONE_DESC
                + ADDRESS_VOLUNTEER_DESC_BOB + PHONE_VOLUNTEER_DESC_BOB + GENDER_DESC_BOB + BIRTHDAY_DESC_BOB;
        descriptor = new EditVolunteerDescriptorBuilder().withPhone(VALID_VOLUNTEER_PHONE_BOB)
                .withEmail(VALID_VOLUNTEER_EMAIL_BOB).withAddress(VALID_VOLUNTEER_ADDRESS_BOB)
                .withGender(VALID_GENDER_BOB).withBirthday(VALID_BIRTHDAY_BOB).build();
        expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withTags().build();
        EditVolunteerCommand expectedCommand = new EditVolunteerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
