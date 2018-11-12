package guitests.guihandles.triviatest.matchtest;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.card.IndexedAnswer;

/**
 * A handler for the UI class {@code AnswerViewHandle}
 */
public class AnswerViewHandle extends NodeHandle<Node> {
    public static final String ID = "#id";
    public static final String ANSWER_TEXT_ID = "#answerText";

    private Label id;
    private Label answerText;

    public AnswerViewHandle(Node answerViewNode) {
        super(answerViewNode);

        id = getChildNode(ID);
        answerText = getChildNode(ANSWER_TEXT_ID);
    }

    /**
     * Checks for equality of with the given IndexedAnswer class.
     */
    public boolean equals(IndexedAnswer answer) {
        return id.getText().equals(String.valueOf(answer.getId()) + ". ")
                && answerText.getText().equals(answer.value);
    }
}
