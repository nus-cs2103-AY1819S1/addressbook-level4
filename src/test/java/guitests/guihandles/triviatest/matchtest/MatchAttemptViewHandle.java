package guitests.guihandles.triviatest.matchtest;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.test.matchtest.MatchAttempt;

/**
 * A handler for the UI class {@code MatchAttemptView}
 */
public class MatchAttemptViewHandle extends NodeHandle<Node> {

    public static final String ATTEMPT_TEXT_ID = "#attemptText";

    private Label attemptText;

    public MatchAttemptViewHandle(Node matchAttemptViewNode) {
        super(matchAttemptViewNode);

        attemptText = getChildNode(ATTEMPT_TEXT_ID);
    }

    public boolean equals(MatchAttempt attempt) {
        return attemptText.getText().equals(attempt.getQuestion() + " ---> " + attempt.getIndexedAnswer());
    }
}
