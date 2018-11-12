package guitests.guihandles.triviatest.matchtest;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.card.IndexedQuestion;

/**
 * A handle for the UI class {@code QuestionView}.
 */
public class QuestionViewHandle extends NodeHandle<Node> {
    public static final String ID = "#id";
    public static final String QUESTION_TEXT_ID = "#questionText";

    private Label id;
    private Label questionText;

    public QuestionViewHandle(Node questionViewNode) {
        super(questionViewNode);

        id = getChildNode(ID);
        questionText = getChildNode(QUESTION_TEXT_ID);
    }

    /**
     * Checks for equality of with the given IndexedQuestion class.
     */
    public boolean equals(IndexedQuestion question) {
        return id.getText().equals(String.valueOf(question.getId()) + ". ")
                && questionText.getText().equals(question.value);
    }
}
