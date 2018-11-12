package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOPIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_GIT;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_NO_TOPIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.CardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder(Q_FLAT_EARTH).withTopics(VALID_TOPIC_PHYSICS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                + TOPIC_DESC_PHYSICS, new AddCommand(expectedCard));

        // multiple questions - last question accepted
        assertParseSuccess(parser, QUESTION_DESC_GIT_COMMIT + QUESTION_DESC_EARTH_FLAT
                + ANSWER_DESC_EARTH_FLAT + TOPIC_DESC_PHYSICS, new AddCommand(expectedCard));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_GIT_COMMIT + ANSWER_DESC_EARTH_FLAT
                + TOPIC_DESC_PHYSICS, new AddCommand(expectedCard));

        // multiple tags - all accepted
        Card expectedPersonMultipleTags = new CardBuilder(Q_FLAT_EARTH).withTopics(VALID_TOPIC_PHYSICS,
                VALID_TOPIC_PHYSICS).build();
        assertParseSuccess(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                + TOPIC_DESC_PHYSICS + TOPIC_DESC_GIT, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Card expectedCard = new CardBuilder(Q_FLAT_EARTH).withTopics().build();
        assertParseSuccess(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT + VALID_TOPIC_NO_TOPIC,
                new AddCommand(expectedCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, ANSWER_DESC_EARTH_FLAT + TOPIC_DESC_PHYSICS, expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + TOPIC_DESC_PHYSICS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_EARTH_FLAT + TOPIC_DESC_PHYSICS,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + INVALID_ANSWER_DESC + TOPIC_DESC_PHYSICS,
                Answer.MESSAGE_ANSWER_CONSTRAINTS);

        // invalid topic
        assertParseFailure(parser, QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT + INVALID_TOPIC_DESC
                + VALID_TOPIC_PHYSICS, Topic.MESSAGE_TOPIC_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + INVALID_ANSWER_DESC + TOPIC_DESC_PHYSICS,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                        + TOPIC_DESC_PHYSICS, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
