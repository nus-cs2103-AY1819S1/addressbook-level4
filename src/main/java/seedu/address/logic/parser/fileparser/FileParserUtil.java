package seedu.address.logic.parser.fileparser;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.fileparser.exceptions.FileParseException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.topic.Topic;

/**
 * Contains methods used for parsing a file to cards and vice-versa.
 */
public class FileParserUtil {
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Invalid file format.";
    public static final String MESSAGE_INVALID_TOPIC_FORMAT = "Topic format.";
    private static final String QUESTION_ANSWER_SEPARATOR = "\t";

    /**
     * Returns true if the trimmed line does not contain any text.
     */
    public static boolean isEmpty(String line) {
        requireNonNull(line);
        line = line.trim();
        return line.isEmpty();
    }

    /**
     * Returns true if the line contains topics (i.e. t/topic).
     * line should not be empty.
     *
     * @throws FileParseException If the line is empty;
     */
    public static boolean isTopic(String line) throws FileParseException {
        requireNonNull(line);

        if (isEmpty(line)) {
            throw new FileParseException(String
                    .format(Messages.MESSAGE_INVALID_FILE_FORMAT, MESSAGE_INVALID_TOPIC_FORMAT));
        }

        return line.contains(PREFIX_TOPIC.toString());
    }

    /**
     * Returns true if the line contains a topic that is in the specified format.
     * i.e. " t/topic". Whitespace before prefix "t/" is required.
     */
    public static boolean isTopicValidFormat(String line) {
        requireNonNull(line);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(line, PREFIX_TOPIC);
        try {
            if (!isPrefixPresent(argMultimap, PREFIX_TOPIC) || !argMultimap.getPreamble().isEmpty()) {
                return false;
            }
            ParserUtil.parseTopics(argMultimap.getAllValues(PREFIX_TOPIC)); // test if topic format is correct
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the line contains a question answer pair that is in the specified format.
     * i.e. "question" + "\t" + "answer".
     */
    public static boolean isQuestionAnswerValidFormat(String line) {
        requireNonNull(line);
        String[] cardString = line.split(QUESTION_ANSWER_SEPARATOR);
        if (cardString.length != 2) {
            return false;
        }
        try {
            Set<Topic> topicSet = new HashSet<>();
            stringToCard(cardString, topicSet);
        } catch (FileParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Parses a string into a set of topics. Each topic must start with a whitespace and a prefix. e.g " t/Topic"
     *
     * @param line The string containing the topics.
     * @return The set of topic parsed.
     * @throws FileParseException If the format of the topic is different from expected.
     */
    public static Set<Topic> parseLineToTopicSet(String line) throws FileParseException {
        requireNonNull(line);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(line, PREFIX_TOPIC);
        try {
            if (!isPrefixPresent(argMultimap, PREFIX_TOPIC) || !argMultimap.getPreamble().isEmpty()) {
                throw new FileParseException(String
                        .format(Messages.MESSAGE_INVALID_FILE_FORMAT, MESSAGE_INVALID_TOPIC_FORMAT));
            }
            return ParserUtil.parseTopics(argMultimap.getAllValues(PREFIX_TOPIC));
        } catch (ParseException pe) {
            throw new FileParseException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    /**
     * Returns true if the prefixes does not contain empty {@code Optional} values in the given.
     *
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix topic) {
        return Stream.of(topic).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Splits string into a question and answer pair string. Using "\t" as
     * a line separator.
     *
     * @param line Line read from text file.
     * @return An array containing a pair of question and answer in that order.
     * @throws FileParseException If the format of the line read is different from the expected.
     */
    public static String[] parseLineToQuestionAnswerPair(String line) throws FileParseException {
        requireNonNull(line);
        String[] cardString = line.split(QUESTION_ANSWER_SEPARATOR);
        if (cardString.length != 2) {
            throw new FileParseException(MESSAGE_INVALID_FILE_FORMAT);
        }
        return cardString;
    }

    /**
     * Converts a question and answer string pair into a card with specified topics.
     * The pair should only consist of 1 question followed by 1 answer. The order is important.
     *
     * @param cardString The question and answer string pair.
     * @return The card created from the question and answer string pair.
     * @throws FileParseException if the question and/ or answer format is different from expected.
     */
    public static Card stringToCard (String[] cardString, Set<Topic> topicSet) throws FileParseException {
        requireNonNull(cardString);
        requireNonNull(topicSet);

        if (cardString.length != 2) {
            throw new FileParseException(MESSAGE_INVALID_FILE_FORMAT);
        }

        try {
            Question question = ParserUtil.parseQuestion(cardString[0]);
            Answer answer = ParserUtil.parseAnswer(cardString[1]);
            return new Card(question, answer, topicSet);
        } catch (ParseException pe) {
            throw new FileParseException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }
}
