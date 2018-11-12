package seedu.address.logic.parser;

import static seedu.address.model.topic.Topic.isValidTopicName;

import seedu.address.logic.commands.LearnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.TopicIsKeywordPredicate;
import seedu.address.model.topic.Topic;

/**
 * Parses input arguments and create a new LearnCommand Object.
 */
public class LearnCommandParser implements Parser<LearnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LearnCommand
     * and returns an LearnCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LearnCommand parse(String args) throws ParseException {
        String topicKeyword = args.trim();

        if (topicKeyword.isEmpty()) {
            return new LearnCommand();
        }

        if (!isValidTopicName(topicKeyword)) {
            throw new ParseException(Topic.MESSAGE_TOPIC_CONSTRAINTS);
        }

        return new LearnCommand(new TopicIsKeywordPredicate(topicKeyword));
    }
}
