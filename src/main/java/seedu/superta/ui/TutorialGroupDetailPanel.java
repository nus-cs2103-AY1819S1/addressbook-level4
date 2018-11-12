package seedu.superta.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.superta.commons.core.LogsCenter;
import seedu.superta.commons.events.ui.AssignmentSelectedEvent;
import seedu.superta.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.superta.commons.events.ui.ViewSessionEvent;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * TutorialGroupDetailPanel to display details of tutorial group.
 */
public class TutorialGroupDetailPanel extends ViewPanelContent {
    private static final String FXML = "TutorialGroupDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TutorialGroupDetailPanel.class.getSimpleName());

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private ListView<Student> students;

    @FXML
    private ListView<Assignment> assignments;

    @FXML
    private ListView<Session> sessions;

    private TutorialGroup tutorialGroup;

    public TutorialGroupDetailPanel(TutorialGroup tutorialGroup) {
        super(FXML);
        this.tutorialGroup = tutorialGroup;
        render();
        setEventHandlerForSelectionChangeEvent();
    }

    public void renderDeleted() {
        name.setText("[Deleted] " + tutorialGroup.getName());
    }

    /**
     * Renders the UI views.
     */
    public void render() {
        id.setText(tutorialGroup.getId());
        name.setText(tutorialGroup.getName());
        students.setItems(tutorialGroup.getStudents().asUnmodifiableObservableList());
        students.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);

                if (empty || student == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    StudentCard card = new StudentCard(student);
                    card.removeAvatar();
                    setGraphic(card.getRoot());
                }
            }
        });
        students.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selected new student.");
                        raise(new StudentPanelSelectionChangedEvent(newValue));
                    }
                }));
        assignments.setItems(tutorialGroup.getAssignments().asUnmodifiableObservableList());
        assignments.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Assignment assignment, boolean empty) {
                super.updateItem(assignment, empty);

                if (empty || assignment == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new AssignmentCard(tutorialGroup, assignment).getRoot());
                }
            }
        });
        sessions.setItems(tutorialGroup.getSessions().asUnmodifiableObservableList());
        sessions.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Session session, boolean empty) {
                super.updateItem(session, empty);

                if (empty || session == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new SessionCard(session, tutorialGroup).getRoot());
                }
            }
        });
        sessions.setOnMouseClicked(event -> {
            raise(new ViewSessionEvent(sessions.getSelectionModel().getSelectedItem(), tutorialGroup));
        });
    }

    public void setEventHandlerForSelectionChangeEvent() {
        assignments.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in assignment detailed view changed.");
                    raise(new AssignmentSelectedEvent(tutorialGroup, newValue));
                }
            });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TutorialGroupDetailPanel)) {
            return false;
        }

        return tutorialGroup.equals(((TutorialGroupDetailPanel) other).tutorialGroup);
    }


    @Override
    public void update(ReadOnlySuperTaClient model) {
        Optional<TutorialGroup> fromModel = model.getTutorialGroup(tutorialGroup.getId());
        if (!fromModel.isPresent()) {
            renderDeleted();
            return;
        }
        this.tutorialGroup = fromModel.get();
        render();
    }
}

