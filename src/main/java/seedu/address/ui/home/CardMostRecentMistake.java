package seedu.address.ui.home;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DateUtil;
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

    @FXML
    private Label timestampOfAttemptText;
    @FXML
    private Label answerOfTheMistakeText;
    @FXML
    private Label expectedAnswerText;


    public CardMostRecentMistake() {
        super(FXML);
        fillInnerParts();
    }

    public CardMostRecentMistake(Attempt mostRecentMistake) {
        super(FXML);

        this.mostRecentMistake = mostRecentMistake;
        fillInnerParts();
    }

    /**
     * Fill the inner components of card experience with the information on the attempts made by the card.
     */
    private void fillInnerParts() {
        if (mostRecentMistake == null) {
            timestampOfAttemptText.setText("-");
            answerOfTheMistakeText.setText("-");
            expectedAnswerText.setText("-");
        } else {
            timestampOfAttemptText.setText(DateUtil.format(mostRecentMistake.getTimestamp()));
            answerOfTheMistakeText.setText(mostRecentMistake.getRawAnswer());
            expectedAnswerText.setText(mostRecentMistake.getAttemptedCard().getAnswer().value);
        }
    }
}
