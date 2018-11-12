package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Question;
import seedu.address.testutil.Assert;

public class XmlAdaptedCardTest {

    private static final String INVALID_QUESTION = "";
    private static final String INVALID_ANSWER = "";
    private static final String INVALID_TOPIC = "#topic";

    private static final String VALID_QUESTION = Q_FLAT_EARTH.getQuestion().toString();
    private static final String VALID_ANSWER = Q_FLAT_EARTH.getAnswer().toString();
    private static final List<XmlAdaptedTopic> VALID_TOPICS = Q_FLAT_EARTH.getTopics().stream()
            .map(XmlAdaptedTopic::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        XmlAdaptedCard person = new XmlAdaptedCard(Q_FLAT_EARTH);
        assertEquals(Q_FLAT_EARTH, person.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        XmlAdaptedCard card =
                new XmlAdaptedCard(INVALID_QUESTION, VALID_ANSWER, VALID_TOPICS);
        String expectedMessage = Question.MESSAGE_QUESTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(null, VALID_ANSWER, VALID_TOPICS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        XmlAdaptedCard card =
                new XmlAdaptedCard(VALID_QUESTION, INVALID_ANSWER, VALID_TOPICS);
        String expectedMessage = Answer.MESSAGE_ANSWER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_QUESTION, null, VALID_TOPICS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidTopics_throwsIllegalValueException() {
        List<XmlAdaptedTopic> invalidTopics = new ArrayList<>(VALID_TOPICS);
        invalidTopics.add(new XmlAdaptedTopic(INVALID_TOPIC));
        XmlAdaptedCard card =
                new XmlAdaptedCard(VALID_QUESTION, VALID_ANSWER, invalidTopics);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }
}
