package seedu.address.ui.test.matchtest;

import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.ui.UiPart;

/**
 * An UI component to display information of {@code Answer}
 */
public class AnswerView extends UiPart<Region> {
    private static final String FXML = "test/matchtest/AnswerView.fxml";

    public final IndexedAnswer answer;
    private final Logger logger = LogsCenter.getLogger(AnswerView.class);

    @FXML
    private HBox answerViewPane;
    @FXML
    private Label id;
    @FXML
    private Label answerText;

    public AnswerView(IndexedAnswer answer) {
        super(FXML);
        this.answer = answer;
        id.setText(answer.getId() + ". ");
        answerText.setText(answer.value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerView)) {
            return false;
        }

        // state check
        AnswerView card = (AnswerView) other;
        return id.getText().equals(card.id.getText())
                && answer.equals(card.answer);
    }
}
