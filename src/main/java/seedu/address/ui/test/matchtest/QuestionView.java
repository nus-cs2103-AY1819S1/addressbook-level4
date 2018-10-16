package seedu.address.ui.test.matchtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.card.Question;
import seedu.address.ui.UiPart;

/**
 * An UI component to display information of {@code Question}
 */
public class QuestionView extends UiPart<Region> {
    private static final String FXML = "/test/matchtest/QuestionView.fxml";

    public final Question question;

    @FXML
    private HBox questionViewPane;
    @FXML
    private Label id;
    @FXML
    private Label questionText;

    public QuestionView(Question question, int displayedIndex) {
        super(FXML);
        this.question = question;
        id.setText(displayedIndex + ". ");
        questionText.setText(question.value);
    }

    public QuestionView(Question question, int displayedIndex, boolean isCorrect) {
        super(FXML);
        this.question = question;
        id.setText(displayedIndex + ". ");
        questionText.setText(question.value);
        if (isCorrect) {
            this.flashBackgroundColor(questionViewPane, new Color(0, 1, 0, 1));
        } else {
            this.flashBackgroundColor(questionViewPane, new Color(1, 0, 0, 1));

        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionView)) {
            return false;
        }

        // state check
        QuestionView card = (QuestionView) other;
        return id.getText().equals(card.id.getText())
                && question.equals(card.question);
    }
}
