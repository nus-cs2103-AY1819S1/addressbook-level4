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
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_TYPE_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_TYPE_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_1;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_1;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_1;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_1;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.EditCommand;
//import seedu.address.logic.commands.EditCommand.EditCarparkDescriptor;
//import seedu.address.model.carpark.Address;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.EditPersonDescriptorBuilder;
//
//public class EditCommandParserTest {
//
//    private static final String TAG_EMPTY = " " + PREFIX_TAG;
//
//    private static final String MESSAGE_INVALID_FORMAT =
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
//
//    private EditCommandParser parser = new EditCommandParser();
//
//    @Test
//    public void parse_missingParts_failure() {
//        // no index specified
//        assertParseFailure(parser, VALID_CARPARK_NUMBER_1, MESSAGE_INVALID_FORMAT);
//
//        // no field specified
//        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);
//
//        // no index and no field specified
//        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidPreamble_failure() {
//        // negative index
//        assertParseFailure(parser, "-5" + CARPARK_NO_DESC_1, MESSAGE_INVALID_FORMAT);
//
//        // zero index
//        assertParseFailure(parser, "0" + CARPARK_NO_DESC_1, MESSAGE_INVALID_FORMAT);
//
//        // invalid arguments being parsed as preamble
//        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
//
//        // invalid prefix being parsed as preamble
//        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        assertParseFailure(parser, "1" + INVALID_CARPARK_NO_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
//        assertParseFailure(parser, "1" + INVALID_CARPARK_TYPE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
//        assertParseFailure(parser, "1" + INVALID_COORDINATE_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
//        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
//        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag
//
//        // invalid phone followed by valid email
//        assertParseFailure(parser, "1" + INVALID_CARPARK_TYPE_DESC + COORDINATE_DESC_1, Phone.MESSAGE_PHONE_CONSTRAINTS);
//
//        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
//        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
//        assertParseFailure(parser, "1" + CARPARK_TYPE_DESC_2 + INVALID_CARPARK_TYPE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);
//
//        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
//        // parsing it together with a valid tag results in error
//        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
//        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
//        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
//
//        // multiple invalid values, but only the first invalid value is captured
//        assertParseFailure(parser, "1" + INVALID_CARPARK_NO_DESC + INVALID_COORDINATE_DESC + VALID_ADDRESS_1 + VALID_CARPARK_TYPE_1,
//                Name.MESSAGE_NAME_CONSTRAINTS);
//    }
//
//    @Test
//    public void parse_allFieldsSpecified_success() {
//        Index targetIndex = INDEX_SECOND_PERSON;
//        String userInput = targetIndex.getOneBased() + CARPARK_TYPE_DESC_2 + TAG_DESC_HUSBAND
//                + COORDINATE_DESC_1 + ADDRESS_DESC_1 + CARPARK_NO_DESC_1 + TAG_DESC_FRIEND;
//
//        EditCarparkDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_CARPARK_NUMBER_1)
//                .withPhone(VALID_CARPARK_TYPE_2).withEmail(VALID_COORDINATE_1).withAddress(VALID_ADDRESS_1)
//                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_someFieldsSpecified_success() {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased() + CARPARK_TYPE_DESC_2 + COORDINATE_DESC_1;
//
//        EditCarparkDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_CARPARK_TYPE_2)
//                .withEmail(VALID_COORDINATE_1).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_oneFieldSpecified_success() {
//        // name
//        Index targetIndex = INDEX_THIRD_PERSON;
//        String userInput = targetIndex.getOneBased() + CARPARK_NO_DESC_1;
//        EditCommand.EditCarparkDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_CARPARK_NUMBER_1).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // phone
//        userInput = targetIndex.getOneBased() + CARPARK_TYPE_DESC_1;
//        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_CARPARK_TYPE_1).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // email
//        userInput = targetIndex.getOneBased() + COORDINATE_DESC_1;
//        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_COORDINATE_1).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // address
//        userInput = targetIndex.getOneBased() + ADDRESS_DESC_1;
//        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_1).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // tags
//        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
//        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_multipleRepeatedFields_acceptsLast() {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased() + CARPARK_TYPE_DESC_1 + ADDRESS_DESC_1 + COORDINATE_DESC_1
//                + TAG_DESC_FRIEND + CARPARK_TYPE_DESC_1 + ADDRESS_DESC_1 + COORDINATE_DESC_1 + TAG_DESC_FRIEND
//                + CARPARK_TYPE_DESC_2 + ADDRESS_DESC_2 + COORDINATE_DESC_2 + TAG_DESC_HUSBAND;
//
//        EditCarparkDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_CARPARK_TYPE_2)
//                .withEmail(VALID_COORDINATE_2).withAddress(VALID_ADDRESS_2).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
//                .build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_invalidValueFollowedByValidValue_success() {
//        // no other valid values specified
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased() + INVALID_CARPARK_TYPE_DESC + CARPARK_TYPE_DESC_2;
//        EditCommand.EditCarparkDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_CARPARK_TYPE_2).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // other valid values specified
//        userInput = targetIndex.getOneBased() + COORDINATE_DESC_2 + INVALID_CARPARK_TYPE_DESC + ADDRESS_DESC_2
//                + CARPARK_TYPE_DESC_2;
//        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_CARPARK_TYPE_2).withEmail(VALID_COORDINATE_2)
//                .withAddress(VALID_ADDRESS_2).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_resetTags_success() {
//        Index targetIndex = INDEX_THIRD_PERSON;
//        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
//
//        EditCarparkDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//}
