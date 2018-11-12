package guitests.guihandles;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import seedu.address.model.test.Attempt;

/**
 * A handler for the {@code CardExperience} of the UI.
 */
public class CardExperienceHandle extends NodeHandle<Node> {

    public static final String CARD_PROGRESS_ID = "#cardProgress";
    public static final String NUM_ATTEMPTS_ID = "#numOfAttemptsText";
    public static final String NUM_CORRECT_ATTEMPTS_ID = "#numCorrectAttemptsText";
    public static final String NUM_WRONG_ATTEMPTS_ID = "#numWrongAttemptsText";

    private final ProgressBar cardProgress;
    private final Label numOfAttemptsText;
    private final Label numCorrectAttemptsText;
    private final Label numWrongAttemptsText;

    public CardExperienceHandle(Node cardExperienceNode) {
        super(cardExperienceNode);
        cardProgress = getChildNode(CARD_PROGRESS_ID);
        numOfAttemptsText = getChildNode(NUM_ATTEMPTS_ID);
        numCorrectAttemptsText = getChildNode(NUM_CORRECT_ATTEMPTS_ID);
        numWrongAttemptsText = getChildNode(NUM_WRONG_ATTEMPTS_ID);
    }

    public String getNumOfAttemptsText() {
        return numOfAttemptsText.getText();
    }

    public String getNumCorrectAttemptsText() {
        return numCorrectAttemptsText.getText();
    }

    public String getNumWrongAttemptsText() {
        return numWrongAttemptsText.getText();
    }

    /**
     * Equality function for CardExperience given a list of attempts.
     */
    public boolean equals(List<Attempt> attempts) {
        long numCorrectAttempts = attempts.stream()
                .filter(Attempt::isCorrect)
                .count();
        long numWrongAttempts = attempts.size() - numCorrectAttempts;
        DoubleProperty correctAttemptRatio = new SimpleDoubleProperty(numCorrectAttempts / (float) attempts.size());

        return cardProgress.progressProperty().getValue().equals(correctAttemptRatio.getValue())
                && getNumOfAttemptsText().equals(String.valueOf(attempts.size()) + " Attempt(s)")
                && getNumCorrectAttemptsText().equals(String.valueOf(numCorrectAttempts))
                && getNumWrongAttemptsText().equals(String.valueOf(numWrongAttempts));
    }
}
