package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditCardDescriptor;
import seedu.address.model.card.Card;
import seedu.address.model.topic.Topic;

/**
 * A utility class for Card.
 */
public class CardUtil {

    /**
     * Returns an add command string for adding the {@code card}.
     */
    public static String getAddCommand(Card card) {
        return AddCommand.COMMAND_WORD + " " + getCardDetails(card);
    }

    /**
     * Returns the part of command string for the given {@code card}'s details.
     */
    public static String getCardDetails(Card card) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + card.getQuestion().value + " ");
        sb.append(PREFIX_ANSWER + card.getAnswer().value + " ");
        card.getTopics().stream().forEach(
            s -> sb.append(PREFIX_TOPIC + s.topicName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCardDescriptor}'s details.
     */
    public static String getEditCardDescriptorDetails(EditCardDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestion().ifPresent(question -> sb.append(PREFIX_QUESTION).append(question.value).append(" "));
        descriptor.getAnswer().ifPresent(answer -> sb.append(PREFIX_ANSWER).append(answer.value).append(" "));
        if (descriptor.getTopics().isPresent()) {
            Set<Topic> topics = descriptor.getTopics().get();
            if (topics.isEmpty()) {
                sb.append(PREFIX_TOPIC);
            } else {
                topics.forEach(s -> sb.append(PREFIX_TOPIC).append(s.topicName).append(" "));
            }
        }
        return sb.toString();
    }
}
