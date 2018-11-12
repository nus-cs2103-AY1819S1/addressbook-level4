package seedu.superta.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.superta.model.assignment.GradeEntry;

// @@author Caephler

/**
 * UI Component for GradeEntry.
 */
public class GradeEntryCard extends UiPart<Region> {
    private static final String FXML = "GradeEntryCard.fxml";

    @FXML
    private Label marks;

    @FXML
    private Label studentId;

    private GradeEntry entry;

    public GradeEntryCard(GradeEntry entry) {
        super(FXML);
        this.entry = entry;

        studentId.setText(entry.studentId.studentId);
        marks.setText(entry.marks.toString());
    }
}
