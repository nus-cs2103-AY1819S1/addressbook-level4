package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;

/**
 * A handler for the {@code CardMostRecentMistake} of the UI.
 */
public class CardMostRecentMistakeHandle extends NodeHandle<Node> {

    public static final String TIMESTAMP_OF_ATTEMPT_ID = "#timestampOfAttemptText";
    public static final String ANSWER_OF_MISTAKE_ID = "#answerOfTheMistakeText";
    public static final String EXPECTED_ANSWER_TEXT_ID = "#expectedAnswerText";
    public static final String EXPECTED_ANSWER_HBOX_ID = "#expectedAnswerBox";

    private final Label timestampOfAttemptText;
    private final TextArea answerOfTheMistakeText;
    private final TextArea expectedAnswerText;
    private final HBox expectedAnswerBox;

    public CardMostRecentMistakeHandle(Node cardMostRecentMistakeNode) {
        super(cardMostRecentMistakeNode);
        timestampOfAttemptText = getChildNode(TIMESTAMP_OF_ATTEMPT_ID);
        answerOfTheMistakeText = getChildNode(ANSWER_OF_MISTAKE_ID);
        expectedAnswerText = getChildNode(EXPECTED_ANSWER_TEXT_ID);
        expectedAnswerBox = getChildNode(EXPECTED_ANSWER_HBOX_ID);
    }

    public String getTimestampOfAttemptText() {
        return timestampOfAttemptText.getText();
    }

    public String getAnswerOfTheMistakeText() {
        return answerOfTheMistakeText.getText();
    }

    public String getExpectedAnswerText() {
        return expectedAnswerText.getText();
    }

    /**
     * Equality function for CardMostRecentMistake given the card and answer.
     */
    public boolean equals(Card card, Attempt mostRecentMistake) {
        if (mostRecentMistake == null) {
            return getTimestampOfAttemptText().equals("-") && getAnswerOfTheMistakeText().equals("-")
                    && !expectedAnswerBox.isVisible();
        }

        if (card.getAnswer().equals(mostRecentMistake.getAttemptedCard().getAnswer())) {
            return getTimestampOfAttemptText().equals(DateUtil.format(mostRecentMistake.getTimestamp()))
                    && getAnswerOfTheMistakeText().equals(mostRecentMistake.getRawAnswer())
                    && !expectedAnswerBox.isVisible();
        } else {
            return getTimestampOfAttemptText().equals(DateUtil.format(mostRecentMistake.getTimestamp()))
                    && getAnswerOfTheMistakeText().equals(mostRecentMistake.getRawAnswer())
                    && getExpectedAnswerText().equals(mostRecentMistake.getAttemptedCard().getAnswer().value);
        }
    }
}
