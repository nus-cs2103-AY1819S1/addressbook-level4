package seedu.superta.ui;

// @@author Caephler

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.GradeEntry;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * A UI component that displays the summary of an {@code Assignment}.
 */
public class AssignmentCard extends UiPart<Region> {
    private static final String FXML = "AssignmentCard.fxml";

    @FXML
    private Label title;

    @FXML
    private Label maxMarks;

    @FXML
    private Label studentsGraded;

    private TutorialGroup tutorialGroup;
    private Assignment assignment;

    public AssignmentCard(TutorialGroup tutorialGroup, Assignment assignment) {
        super(FXML);
        this.assignment = assignment;
        this.tutorialGroup = tutorialGroup;

        title.setText(assignment.getTitle().toString());
        maxMarks.setText("Max Marks: " + assignment.getMaxMarks());
        updateStudentCount();

        tutorialGroup.getStudents().asUnmodifiableObservableList().addListener(
            (ListChangeListener<? super Student>) change -> {
                updateStudentCount();
            }
        );

        assignment.getGradebook().asUnmodifiableObservableList().addListener(
            (ListChangeListener<? super GradeEntry>) change -> {
                updateStudentCount();
            }
        );
    }

    /**
     * Updates the student count in this assignment.
     */
    public void updateStudentCount() {
        int total = tutorialGroup.getStudents().size();
        long graded = assignment.getGradebook().stream().count();

        studentsGraded.setText("" + graded + " / " + total + " students graded");
    }
}
