package seedu.superta.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import seedu.superta.commons.core.LogsCenter;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;

// @@author Caephler
/**
 * UI Component for showing Student Details.
 */
public class StudentDetailPanel extends ViewPanelContent {
    private static final String FXML = "StudentDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentDetailPanel.class.getSimpleName());

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label phoneNumber;

    @FXML
    private ListView<Feedback> feedback;

    @FXML
    private FlowPane tags;

    private Student student;

    public StudentDetailPanel(Student student) {
        super(FXML);
        this.student = student;
        this.registerAsAnEventHandler(this);
        render();
    }

    /**
     * Renders the UI views.
     */
    private void render() {
        id.setText(student.getStudentId().studentId);
        name.setText(student.getName().fullName);
        email.setText("Email: " + student.getEmail().value);
        phoneNumber.setText("Phone: " + student.getPhone().value);
        tags.getChildren().clear();
        student.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        feedback.setItems(student.getFeedback());
        feedback.setCellFactory(listView -> new ListCell<>() {
            @Override
            public void updateItem(Feedback feedback, boolean empty) {
                super.updateItem(feedback, empty);
                if (empty || feedback == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new FeedbackCard(feedback).getRoot());
                }
            }
        });
    }

    /**
     * Renders if the model student is deleted.
     */
    private void renderDeleted() {
        name.setText("[Deleted] " + student.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof StudentDetailPanel)) {
            return false;
        }

        return student.equals(((StudentDetailPanel) other).student);
    }

    @Override
    public void update(ReadOnlySuperTaClient model) {
        Optional<Student> updateTarget = model.getStudentList()
            .stream().filter(student -> student.isSameId(this.student))
            .findFirst();
        if (updateTarget.isPresent()) {
            Student fromModel = updateTarget.get();
            this.student = fromModel;
            render();
        } else {
            Platform.runLater(() -> renderDeleted());
        }
    }
}
