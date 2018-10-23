package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AnakinCommandTestUtil.INVALID_ANSWER;
import static seedu.address.logic.commands.AnakinCommandTestUtil.INVALID_QUESTION;
import static seedu.address.logic.commands.AnakinCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_ANSWER_A;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_ANSWER_B;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_QUESTION_A;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_QUESTION_B;
import static seedu.address.logic.parser.AnakinCommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.AnakinCommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.AnakinTypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.AnakinTypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.AnakinTypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.anakincommands.AnakinEditCardCommand;
import seedu.address.logic.anakincommands.AnakinEditCardCommand.EditCardDescriptor;
import seedu.address.logic.anakinparser.AnakinEditCardCommandParser;
import seedu.address.model.anakindeck.AnakinQuestion;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class AnakinEditCardParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnakinEditCardCommand.MESSAGE_USAGE);

    private AnakinEditCardCommandParser parser = new AnakinEditCardCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AnakinEditCardCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_QUESTION + VALID_QUESTION_A,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_QUESTION + VALID_QUESTION_A,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // All tests involve valid indices

        // invalid question
        assertParseFailure(parser, "1" + PREFIX_QUESTION + INVALID_QUESTION,
                AnakinQuestion.MESSAGE_QUESTION_CONSTRAINTS); // invalid name

        // invalid question followed by valid answer
        assertParseFailure(parser, "1" + PREFIX_QUESTION + INVALID_QUESTION +
            " " + PREFIX_ANSWER + VALID_ANSWER_A, AnakinQuestion.MESSAGE_QUESTION_CONSTRAINTS);

        // valid question followed by invalid question. The test case for invalid question followed by valid question
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PREFIX_QUESTION + VALID_QUESTION_A +
            " " + PREFIX_QUESTION + INVALID_QUESTION,
                AnakinQuestion.MESSAGE_QUESTION_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PREFIX_QUESTION + INVALID_QUESTION +
                        " " + PREFIX_ANSWER + INVALID_ANSWER,
                AnakinQuestion.MESSAGE_QUESTION_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CARD;
        String userInput = targetIndex.getOneBased() + PREAMBLE_WHITESPACE
                + PREFIX_QUESTION + VALID_QUESTION_A + " " +
                PREFIX_ANSWER + INVALID_ANSWER;

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_A)
                .withAnswer(VALID_ANSWER_A).build();
        AnakinEditCardCommand expectedCommand = new AnakinEditCardCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // Question
        Index targetIndex = INDEX_THIRD_CARD;
        String userInput = targetIndex.getOneBased() + PREAMBLE_WHITESPACE +
                PREFIX_QUESTION + VALID_QUESTION_A;
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().
                withQuestion(VALID_QUESTION_A).build();
        AnakinEditCardCommand expectedCommand = new AnakinEditCardCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Answer
        userInput = targetIndex.getOneBased() + PREAMBLE_WHITESPACE +
                PREFIX_ANSWER + VALID_ANSWER_A;
        descriptor = new EditCardDescriptorBuilder().
                withAnswer(VALID_ANSWER_A).build();
        expectedCommand = new AnakinEditCardCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + PREAMBLE_WHITESPACE +
                PREFIX_QUESTION + VALID_QUESTION_A + " " +
                PREFIX_QUESTION + VALID_QUESTION_B + " " +
                PREFIX_ANSWER + VALID_QUESTION_A + " " +
                PREFIX_ANSWER + VALID_ANSWER_B;

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_B).withAnswer(VALID_ANSWER_B)
                .build();
        AnakinEditCardCommand expectedCommand = new AnakinEditCardCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + " " +
                PREFIX_QUESTION + INVALID_QUESTION  +
                PREFIX_QUESTION + VALID_QUESTION_A;
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_A).build();
        AnakinEditCardCommand expectedCommand = new AnakinEditCardCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() +  " " +
                PREFIX_QUESTION + INVALID_QUESTION  +
                PREFIX_ANSWER + VALID_ANSWER_A +
                PREFIX_QUESTION + VALID_QUESTION_A;
        descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_A)
                .withAnswer(VALID_ANSWER_A)
                .build();
        expectedCommand = new AnakinEditCardCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
