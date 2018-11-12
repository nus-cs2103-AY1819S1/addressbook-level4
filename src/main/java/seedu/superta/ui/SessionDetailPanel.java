package seedu.superta.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.attendance.Attendance;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.UniqueStudentList;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Details of a Session in the UI.
 */
public class SessionDetailPanel extends ViewPanelContent {
    public static final String FXML = "SessionDetailPanel.fxml";

    @FXML
    private Label tutorialGroupId;

    @FXML
    private Label name;

    @FXML
    private Label attendance;

    @FXML
    private ListView<Attendance> attendanceListView;

    @FXML
    private ListView<Student> absentListView;

    private Session session;
    private TutorialGroup tutorialGroup;

    public SessionDetailPanel(Session session, TutorialGroup tutorialGroup) {
        super(FXML);
        this.session = session;
        this.tutorialGroup = tutorialGroup;
        render();
    }

    /**
     * Renders the component view.
     */
    public void render() {
        tutorialGroupId.setText("Tutorial Group: " + tutorialGroup.getId());
        name.setText(session.getSessionName());
        updateAttendanceCount();
        ObservableList<Attendance> attended = session.asUnmodifiableObservableList();
        UniqueStudentList notAttended = tutorialGroup.getStudents().clone();
        for (Attendance attendance : attended) {
            notAttended.removeStudentWithId(attendance.getStudentId());
        }

        attendanceListView.setItems(session.asUnmodifiableObservableList());
        attendanceListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            public void updateItem(Attendance attendance, boolean empty) {
                super.updateItem(attendance, empty);

                if (empty || attendance == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label label = new Label(attendance.getStudentId().studentId);
                    label.setPadding(new Insets(5, 5, 5, 15));
                    setGraphic(label);
                }
            }
        });

        absentListView.setItems(notAttended.asUnmodifiableObservableList());
        absentListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            public void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);

                if (empty || student == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label label = new Label(student.getStudentId().studentId);
                    label.setPadding(new Insets(5, 5, 5, 15));
                    setGraphic(label);
                }
            }
        });
    }

    /**
     * Sets the attendance count in the view
     */
    private void setAttendanceCount(int attendedCount, int totalCount) {
        double percent = ((double) attendedCount) / totalCount * 100;
        attendance.setText("Attendance: " + attendedCount + " / " + totalCount + " ("
            + String.format("%.2f", percent) + "%)");
    }

    /**
     * Updates the attendance count using the internal session and tutorial group details.
     */
    private void updateAttendanceCount() {
        setAttendanceCount(session.asUnmodifiableObservableSet().size(),
                tutorialGroup.getStudents().size());
    }

    @Override
    public void update(ReadOnlySuperTaClient model) {
        Optional<TutorialGroup> tutorialGroupOptional = model.getTutorialGroup(tutorialGroup.getId());
        if (!tutorialGroupOptional.isPresent()) {
            return;
        }
        TutorialGroup tutorialGroup = tutorialGroupOptional.get();
        Optional<Session> sessionOptional = tutorialGroup.getSession(this.session);
        if (!sessionOptional.isPresent()) {
            return;
        }
        Session newSession = sessionOptional.get();
        this.session = newSession;
        this.tutorialGroup = tutorialGroup;
        render();
    }
}
