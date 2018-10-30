package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONDATE_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONLOCATION_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONNAME_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONDATE_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONDATE_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONLOCATION_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONLOCATION_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandOccasionTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.TAG_DESC_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.TAG_DESC_STUDY;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOccasions.EXAM_2103;

import org.junit.Test;

import seedu.address.logic.commands.AddOccasionCommand;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.OccasionBuilder;

public class AddOccasionCommandParserTest {
    private AddOccasionCommandParser parser = new AddOccasionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Occasion expectedOccasion = new OccasionBuilder(EXAM_2103).withTags(VALID_TAG_STUDY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY, new AddOccasionCommand(expectedOccasion));

        // multiple occasion name - last occasion name accepted
        assertParseSuccess(parser, OCCASIONNAME_DESC_TWO + OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY, new AddOccasionCommand(expectedOccasion));

        // multiple occasion date - last occasion date accepted
        assertParseSuccess(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_TWO + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY, new AddOccasionCommand(expectedOccasion));

        // multiple occasion location - last occasion location accepted
        assertParseSuccess(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_TWO + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY,
                new AddOccasionCommand(expectedOccasion));

        // multiple tags - all accepted
        Occasion expectedOccasionMultipleTags = new OccasionBuilder(EXAM_2103)
                .withTags(VALID_TAG_STUDY, VALID_TAG_SLEEP).build();
        assertParseSuccess(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY + TAG_DESC_SLEEP,
                new AddOccasionCommand(expectedOccasionMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Occasion expectedOccasion = new OccasionBuilder(EXAM_2103).withTags().build();
        assertParseSuccess(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_ONE, new AddOccasionCommand(expectedOccasion));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOccasionCommand.MESSAGE_USAGE);

        // missing occasion name prefix
        assertParseFailure(parser, VALID_OCCASIONNAME_ONE + OCCASIONDATE_DESC_ONE + OCCASIONLOCATION_DESC_ONE,
                expectedMessage);

        // missing occasion date prefix
        assertParseFailure(parser, OCCASIONNAME_DESC_ONE + VALID_OCCASIONDATE_ONE + OCCASIONLOCATION_DESC_ONE,
                expectedMessage);

        // missing occasion location prefix
        assertParseFailure(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE + VALID_OCCASIONLOCATION_ONE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_OCCASIONNAME_ONE + VALID_OCCASIONDATE_ONE
                        + VALID_OCCASIONLOCATION_ONE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid occasion name
        assertParseFailure(parser, INVALID_OCCASIONNAME_DESC + OCCASIONDATE_DESC_ONE
                + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY, OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);

        // invalid occasion Date
        assertParseFailure(parser, OCCASIONNAME_DESC_ONE + INVALID_OCCASIONDATE_DESC
                + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY, OccasionDate.MESSAGE_OCCASIONDATE_CONSTRAINTS);

        // invalid occasion Location
        assertParseFailure(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                + INVALID_OCCASIONLOCATION_DESC + TAG_DESC_STUDY,
                OccasionLocation.MESSAGE_OCCASIONLOCATION_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE + OCCASIONLOCATION_DESC_ONE
                + INVALID_TAG_DESC + VALID_TAG_SLEEP, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_OCCASIONNAME_DESC + INVALID_OCCASIONDATE_DESC
                        + OCCASIONLOCATION_DESC_ONE, OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + OCCASIONNAME_DESC_ONE + OCCASIONDATE_DESC_ONE
                        + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOccasionCommand.MESSAGE_USAGE));
    }
}
