package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.topic.Topic;

/**
 * JAXB-friendly version of the Card.
 */
public class XmlAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    @XmlElement(required = true)
    private String question;
    @XmlElement(required = true)
    private String answer;

    @XmlElement
    private List<XmlAdaptedTopic> topic = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCard() {}

    /**
     * Constructs an {@code XmlAdaptedCard} with the given card details.
     */
    public XmlAdaptedCard(String question, String answer, List<XmlAdaptedTopic> topic) {
        this.question = question;
        this.answer = answer;
        if (topic != null) {
            this.topic = new ArrayList<>(topic);
        }
    }

    /**
     * Converts a given Card into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCard
     */
    public XmlAdaptedCard(Card source) {
        question = source.getQuestion().value;
        answer = source.getAnswer().value;
        topic = source.getTopics().stream()
                .map(XmlAdaptedTopic::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted card object into the model's Card object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card
     */
    public Card toModelType() throws IllegalValueException {
        final List<Topic> cardTopics = new ArrayList<>();
        for (XmlAdaptedTopic topic : topic) {
            cardTopics.add(topic.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_QUESTION_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        final Set<Topic> modelTopics = new HashSet<>(cardTopics);
        return new Card(modelQuestion, modelAnswer, modelTopics);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCard)) {
            return false;
        }

        XmlAdaptedCard otherCard = (XmlAdaptedCard) other;
        return Objects.equals(question, otherCard.question)
                && Objects.equals(answer, otherCard.answer)
                && topic.equals(otherCard.topic);
    }
}
