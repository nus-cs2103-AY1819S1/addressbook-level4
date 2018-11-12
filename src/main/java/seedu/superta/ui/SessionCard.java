package seedu.superta.ui;

import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.superta.model.attendance.Attendance;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler

/**
 * Card for displaying sessions.
 */
public class SessionCard extends UiPart<Region> {
    public static final String FXML = "SessionCard.fxml";

    @FXML
    private Label name;

    @FXML
    private Label attendanceCount;

    private Session session;
    private TutorialGroup tutorialGroup;

    public SessionCard(Session session, TutorialGroup tutorialGroup) {
        super(FXML);
        this.session = session;
        this.tutorialGroup = tutorialGroup;
        name.setText(session.getSessionName());
        setListeners();
        updateAttendanceCount();
    }

    private void setListeners() {
        session.asUnmodifiableObservableSet().addListener(
            (SetChangeListener<? super Attendance>) change -> {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateAttendanceCount();
                }
            }
        );
    }

    private void updateAttendanceCount() {
        setAttendanceCount(session.asUnmodifiableObservableSet().size(),
                           tutorialGroup.getStudents().size());
    }

    private void setAttendanceCount(int count, int totalCount) {
        attendanceCount.setText("" + count + " / " + totalCount + " attended.");
    }
}
