package seedu.address.ui.test.matchtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.IndexedQuestion;
import seedu.address.ui.UiPart;

/**
 * An UI component to display information of {@code Question}
 */
public class QuestionView extends UiPart<Region> {
    private static final String FXML = "test/matchtest/QuestionView.fxml";

    public final IndexedQuestion question;

    @FXML
    private HBox questionViewPane;
    @FXML
    private Label id;
    @FXML
    private Label questionText;

    public QuestionView(IndexedQuestion question) {
        super(FXML);
        this.question = question;
        id.setText(question.getId() + ". ");
        questionText.setText(question.value);
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
