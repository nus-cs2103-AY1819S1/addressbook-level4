package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Question;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.test.TimeLimit;
import seedu.address.model.topic.Topic;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NO_FILE_NAME = "No path name specified.";

    /**
     * Parses {@code pathName} into an {@code file} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if no path name is specified.
     */
    public static File parsePath(String pathName) throws ParseException {
        String trimmedPathName = pathName.trim();
        requireNonNull(trimmedPathName);
        if (trimmedPathName.isEmpty()) {
            throw new ParseException(MESSAGE_NO_FILE_NAME);
        }
        return new File(trimmedPathName);
    }
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String question} into an {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static Question parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_QUESTION_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code String answer} into an {@code Answer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code answer} is invalid.
     */
    public static Answer parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (!Answer.isValidAnswer(trimmedAnswer)) {
            throw new ParseException(Answer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        return new Answer(trimmedAnswer);
    }

    /**
     * Parses a {@code String topic} into a {@code Topic}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code topic} is invalid.
     */
    public static Topic parseTopic(String topic) throws ParseException {
        requireNonNull(topic);
        String trimmedTopic = topic.trim();
        if (!Topic.isValidTopicName(trimmedTopic)) {
            throw new ParseException(Topic.MESSAGE_TOPIC_CONSTRAINTS);
        }
        return new Topic(trimmedTopic);
    }

    /**
     * Parses {@code Collection<String> topics} into a {@code Set<Topic>}.
     */
    public static Set<Topic> parseTopics(Collection<String> topics) throws ParseException {
        requireNonNull(topics);
        final Set<Topic> topicSet = new HashSet<>();
        for (String topicName : topics) {
            topicSet.add(parseTopic(topicName));
        }
        return topicSet;
    }

    /**
     * Parses {@code String timeLimit} into a float
     *
     * @throws ParseException if the given {@code timeLimit} is invalid
     */
    public static TimeLimit parseTimeLimit(String timeLimit) throws ParseException {
        requireNonNull(timeLimit);
        String trimmedTimeLimit = timeLimit.trim();
        if (!TimeLimit.isValidTimeLimit(timeLimit)) {
            throw new ParseException(TimeLimit.MESSAGE_TIME_LIMIT_CONSTRAINTS);
        }
        return new TimeLimit(trimmedTimeLimit);
    }
}
