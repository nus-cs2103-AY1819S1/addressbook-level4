package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.topic.Topic;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_QUESTION = "Why is the world round?";
    public static final String DEFAULT_ANSWER = "Because of gravity!";
    public static final List<Topic> DEFAULT_TOPIC = Arrays.asList(new Topic[]{new Topic("Physics")});

    private Question question;
    private Answer answer;
    private Set<Topic> topics;

    public CardBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new Answer(DEFAULT_ANSWER);
        topics = new HashSet<>(DEFAULT_TOPIC);
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        question = cardToCopy.getQuestion();
        answer = cardToCopy.getAnswer();
        topics = new HashSet<>(cardToCopy.getTopics());
    }

    /**
     * Sets the {@code question} of the {@code Card} that we are building.
     */
    public CardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Parses the {@code topics} into a {@code Set<Topic>} and set it to the {@code Card} that we are building.
     */
    public CardBuilder withTopics(String ... topics) {
        this.topics = SampleDataUtil.getTopicSet(topics);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Card} that we are building.
     */
    public CardBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    public Card build() {
        return new Card(question, answer, topics);
    }
}
