package seedu.superta.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.superta.model.student.Feedback;

// @@author Caephler

/**
 * Card to display feedback
 */
public class FeedbackCard extends UiPart<Region> {
    private static final String FXML = "FeedbackCard.fxml";

    @FXML
    private Label feedback;

    private Feedback feedbackModel;

    public FeedbackCard(Feedback feedback) {
        super(FXML);
        this.feedbackModel = feedback;

        this.feedback.setText(feedbackModel.value);
    }
}
