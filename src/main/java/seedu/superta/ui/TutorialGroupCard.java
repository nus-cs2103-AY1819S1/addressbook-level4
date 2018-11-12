package seedu.superta.ui;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * A UI component that displays information of a {@code TutorialGroup}
 */
public class TutorialGroupCard extends UiPart<Region> {
    private static final String FXML = "TutorialGroupCard.fxml";

    public final TutorialGroup tutorialGroup;

    @javafx.fxml.FXML
    private Label name;

    @javafx.fxml.FXML
    private Label id;

    @javafx.fxml.FXML
    private Label numStudents;

    @javafx.fxml.FXML
    private Label numAssignments;

    public TutorialGroupCard(TutorialGroup tutorialGroup) {
        super(FXML);
        this.tutorialGroup = tutorialGroup;

        name.setText(String.format("Name: " + tutorialGroup.getName()));
        id.setText(String.format("ID: " + tutorialGroup.getId()));
        tutorialGroup.getStudents().asUnmodifiableObservableList().addListener(
            (ListChangeListener<? super Student>) change -> {
                updateStudentsSize();
            }
        );
        tutorialGroup.getAssignments().asUnmodifiableObservableList().addListener(
            (ListChangeListener<? super Assignment>) change -> {
                updateAssignmentsSize();
            }
        );
        updateStudentsSize();
        updateAssignmentsSize();
    }

    private void updateStudentsSize() {
        numStudents.setText(tutorialGroup.getStudents().size() + " students");
    }

    private void updateAssignmentsSize() {
        numAssignments.setText(tutorialGroup.getAssignments().size() + " assignments");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TutorialGroupCard)) {
            return false;
        }

        TutorialGroupCard card = (TutorialGroupCard) other;
        return tutorialGroup.equals(card.tutorialGroup);
    }
}
