//package seedu.address.logic.parser;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.COORDINATE_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.COORDINATE_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_COORDINATE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARPARK_NO_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARPARK_TYPE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_NO_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_NO_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_TYPE_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_TYPE_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
//import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalPersons.AMY;
//import static seedu.address.testutil.TypicalPersons.BOB;
//
//import org.junit.Test;
//
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.model.carpark.Address;
//import seedu.address.model.person.Email;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.Phone;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.CarparkBuilder;
//
//public class AddCommandParserTest {
//    private AddCommandParser parser = new AddCommandParser();
//
//    @Test
//    public void parse_allFieldsPresent_success() {
//        Person expectedPerson = new CarparkBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
//
//        // whitespace only preamble
//        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2
//                + ADDRESS_DESC_2 + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
//
//        // multiple names - last name accepted
//        assertParseSuccess(parser, CARPARK_NO_DESC_1 + CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2
//                + ADDRESS_DESC_2 + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
//
//        // multiple phones - last phone accepted
//        assertParseSuccess(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_1 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2
//                + ADDRESS_DESC_2 + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
//
//        // multiple emails - last email accepted
//        assertParseSuccess(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_1 + COORDINATE_DESC_2
//                + ADDRESS_DESC_2 + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
//
//        // multiple addresses - last address accepted
//        assertParseSuccess(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + ADDRESS_DESC_1
//                + ADDRESS_DESC_2 + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
//
//        // multiple tags - all accepted
//        Person expectedPersonMultipleTags = new CarparkBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
//                .build();
//        assertParseSuccess(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + ADDRESS_DESC_2
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
//    }
//
//    @Test
//    public void parse_optionalFieldsMissing_success() {
//        // zero tags
//        Person expectedPerson = new CarparkBuilder(AMY).withTags().build();
//        assertParseSuccess(parser, CARPARK_NO_DESC_1 + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1 + ADDRESS_DESC_1,
//                new AddCommand(expectedPerson));
//    }
//
//    @Test
//    public void parse_compulsoryFieldMissing_failure() {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
//
//        // missing name prefix
//        assertParseFailure(parser, VALID_CARPARK_NUMBER_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + ADDRESS_DESC_2,
//                expectedMessage);
//
//        // missing phone prefix
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + VALID_CARPARK_TYPE_2 + COORDINATE_DESC_2 + ADDRESS_DESC_2,
//                expectedMessage);
//
//        // missing email prefix
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + VALID_COORDINATE_2 + ADDRESS_DESC_2,
//                expectedMessage);
//
//        // missing address prefix
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + VALID_ADDRESS_2,
//                expectedMessage);
//
//        // all prefixes missing
//        assertParseFailure(parser, VALID_CARPARK_NUMBER_2 + VALID_CARPARK_TYPE_2 + VALID_COORDINATE_2 + VALID_ADDRESS_2,
//                expectedMessage);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        // invalid name
//        assertParseFailure(parser, INVALID_CARPARK_NO_DESC + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + ADDRESS_DESC_2
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);
//
//        // invalid phone
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + INVALID_CARPARK_TYPE_DESC + COORDINATE_DESC_2 + ADDRESS_DESC_2
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);
//
//        // invalid email
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + INVALID_COORDINATE_DESC + ADDRESS_DESC_2
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);
//
//        // invalid address
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + INVALID_ADDRESS_DESC
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);
//
//        // invalid tag
//        assertParseFailure(parser, CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + ADDRESS_DESC_2
//                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);
//
//        // two invalid values, only first invalid value reported
//        assertParseFailure(parser, INVALID_CARPARK_NO_DESC + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2 + INVALID_ADDRESS_DESC,
//                Name.MESSAGE_NAME_CONSTRAINTS);
//
//        // non-empty preamble
//        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_2
//                + ADDRESS_DESC_2 + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//    }
//}
