package seedu.address.ui.home;

import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.test.Attempt;
import seedu.address.ui.UiPart;

/**
 * A UI class to represent the component that displays the number of correct and wrong attempts made by the card.
 */
public class CardExperience extends UiPart<Region> {

    private static final String FXML = "home/CardExperience.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private List<Attempt> attemptsByCard;

    @FXML
    private ProgressBar cardProgress;
    @FXML
    private Label numOfAttemptsText;
    @FXML
    private Label numCorrectAttemptsText;
    @FXML
    private Label numWrongAttemptsText;

    public CardExperience(List<Attempt> attemptsByCard) {
        super(FXML);

        this.attemptsByCard = attemptsByCard;
        fillInnerParts();
    }

    /**
     * Fill the inner components of card experience with the information on the attempts made by the card.
     */
    private void fillInnerParts() {
        long numCorrectAttempts = attemptsByCard.stream()
                .filter(Attempt::isCorrect)
                .count();
        long numWrongAttempts = attemptsByCard.size() - numCorrectAttempts;
        DoubleProperty correctAttemptRatio = new SimpleDoubleProperty(
                numCorrectAttempts / (float) attemptsByCard.size());

        cardProgress.progressProperty().bind(correctAttemptRatio);
        numOfAttemptsText.setText(String.valueOf(attemptsByCard.size()) + " Attempt(s)");
        numCorrectAttemptsText.setText(String.valueOf(numCorrectAttempts));
        numWrongAttemptsText.setText(String.valueOf(numWrongAttempts));
    }
}
