package seedu.superta.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.superta.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on SuperTaClient level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label studentId;
    @FXML
    private HBox avatarParent;
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        setLabels();
    }

    public StudentCard(Student student) {
        super(FXML);
        this.student = student;
        setLabels();
    }

    public void setLabels() {
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        email.setText(student.getEmail().value);
        studentId.setText(student.getStudentId().studentId);
        student.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Removes the avatar from this card view.
     */
    public void removeAvatar() {
        avatarParent.paddingProperty().setValue(Insets.EMPTY);
        avatarParent.getChildren().clear();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
