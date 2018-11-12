package seedu.address.ui.home;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;
import seedu.address.ui.UiPart;

/**
 * A UI class to represent the component that display the latest mistake made by the user of that card. This component
 * is part of {@code CardInfoPanel}.
 */
public class CardMostRecentMistake extends UiPart<Region> {

    private static final String FXML = "home/CardMostRecentMistake.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Attempt mostRecentMistake;
    private Card card;

    @FXML
    private Label timestampOfAttemptText;
    @FXML
    private TextArea answerOfTheMistakeText;
    @FXML
    private TextArea expectedAnswerText;
    @FXML
    private HBox expectedAnswerBox;

    public CardMostRecentMistake() {
        super(FXML);
    }

    public CardMostRecentMistake(Card card) {
        super(FXML);
        this.card = card;
        fillInnerParts();
    }

    public CardMostRecentMistake(Card card, Attempt mostRecentMistake) {
        super(FXML);

        this.mostRecentMistake = mostRecentMistake;
        this.card = card;
        fillInnerParts();
    }

    /**
     * Fill the inner components of card experience with the information on the attempts made by the card.
     */
    private void fillInnerParts() {
        if (mostRecentMistake == null) {
            timestampOfAttemptText.setText("-");
            answerOfTheMistakeText.setText("-");
            expectedAnswerBox.setVisible(false);
        } else {
            timestampOfAttemptText.setText(DateUtil.format(mostRecentMistake.getTimestamp()));
            answerOfTheMistakeText.setText(mostRecentMistake.getRawAnswer());

            Answer expectedAnswer = mostRecentMistake.getAttemptedCard().getAnswer();
            if (expectedAnswer.equals(card.getAnswer())) {
                expectedAnswerBox.setVisible(false);
            } else {
                expectedAnswerText.setText(expectedAnswer.value);
            }
        }
    }
}
