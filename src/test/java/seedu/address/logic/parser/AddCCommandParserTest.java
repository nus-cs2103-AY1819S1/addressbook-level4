package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GIT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHYSICS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;

import org.junit.Test;

import seedu.address.logic.commands.AddCCommand;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;

public class AddCCommandParserTest {
    private AddCCommandParser parser = new AddCCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder(Q_FLAT_EARTH).withTags(VALID_TAG_PHYSICS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                + TAG_DESC_PHYSICS, new AddCCommand(expectedCard));

        // multiple questions - last question accepted
        assertParseSuccess(parser, QUESTION_DESC_GIT_COMMIT + QUESTION_DESC_EARTH_FLAT
                + ANSWER_DESC_EARTH_FLAT + TAG_DESC_PHYSICS, new AddCCommand(expectedCard));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_GIT_COMMIT + ANSWER_DESC_EARTH_FLAT
                + TAG_DESC_PHYSICS, new AddCCommand(expectedCard));

        // multiple tags - all accepted
        Card expectedPersonMultipleTags = new CardBuilder(Q_FLAT_EARTH).withTags(VALID_TAG_PHYSICS, VALID_TAG_PHYSICS)
                .build();
        assertParseSuccess(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                + TAG_DESC_PHYSICS + TAG_DESC_GIT, new AddCCommand(expectedPersonMultipleTags));
    }

    // TODO Currently all the fields are compulsory for cad. May need to change tag to non compulsory field.
    //    @Test
    //    public void parse_optionalFieldsMissing_success() {
    //        // zero tags
    //        Person expectedCard = new PersonBuilder(AMY).withTags().build();
    //        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
    //                new AddCCommand(expectedCard));
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, ANSWER_DESC_EARTH_FLAT + TAG_DESC_PHYSICS, expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + TAG_DESC_PHYSICS, expectedMessage);

        // missing tag prefix
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_EARTH_FLAT + TAG_DESC_PHYSICS,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + INVALID_ANSWER_DESC + TAG_DESC_PHYSICS,
                Answer.MESSAGE_ANSWER_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT + INVALID_TAG_DESC
                + VALID_TAG_PHYSICS, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + INVALID_ANSWER_DESC + TAG_DESC_PHYSICS,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                        + TAG_DESC_PHYSICS, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCCommand.MESSAGE_USAGE));
    }
}
